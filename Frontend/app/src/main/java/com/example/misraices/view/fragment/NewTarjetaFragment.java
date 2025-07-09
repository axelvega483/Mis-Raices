package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentNewTarjetaBinding;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class NewTarjetaFragment extends Fragment {
    private FragmentNewTarjetaBinding binding;
    private TarjetaViewModel tarjetaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable binLookupRunnable;

    public NewTarjetaFragment() {
        // Required empty public constructor
    }


    public static NewTarjetaFragment newInstance() {
        NewTarjetaFragment fragment = new NewTarjetaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewTarjetaBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);

        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        String[] tiposTarjeta = {"Visa", "MasterCard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                tiposTarjeta
        );
        binding.TipoEditText.setAdapter(adapter);
        binding.TipoEditText.setThreshold(1);

        binding.numeroEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6) {
                    final String bin = s.toString().substring(0, 6);
                    if (binLookupRunnable != null) {
                        handler.removeCallbacks(binLookupRunnable);
                    }
                    binLookupRunnable = () -> detectarTipo(bin);
                    handler.postDelayed(binLookupRunnable, 300);
                } else {
                    if (binLookupRunnable != null) {
                        handler.removeCallbacks(binLookupRunnable);
                    }

                    binding.TipoEditText.setText("");
                    binding.logoMarcaImageView.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void initListener() {
        binding.btnNuevaTarjeta.setOnClickListener(view -> {
            usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
                String numero = binding.numeroEditText.getText().toString().trim();
                String titular = binding.titularEditText.getText().toString().trim();
                String fecha = binding.fechaEditText.getText().toString().trim();
                String tipo = binding.TipoEditText.getText().toString().trim();
                String codigo = binding.codigoEditText.getText().toString().trim();

                if (!validarCampos(numero, titular, fecha, tipo, codigo)) return;

                TarjetaCredito tarjeta = new TarjetaCredito();
                tarjeta.setUsuario(usuario.getData());
                tarjeta.setNumero(numero);
                tarjeta.setTitular(titular);
                tarjeta.setFechaVencimiento(fecha);
                tarjeta.setTipo(tipo);
                tarjeta.setCodigoSeguridad(codigo);

                tarjetaViewModel.crearTarjeta(tarjeta);
                tarjetaViewModel.setTarjetaLiveData(tarjeta);
                Log.e("Tarjeta creada", tarjeta.toString());

                Bundle result = new Bundle();
                result.putBoolean("forzar", true);
                getParentFragmentManager().setFragmentResult("recargar_tarjetas", result);
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }

    private boolean validarCampos(String numero, String titular, String fecha, String tipo, String codigo) {
        if (numero.isEmpty() || !numero.matches("\\d{13,19}")) {
            mostrarToast("Número de tarjeta inválido", "El número de tarjeta debe tener entre 13 y 19 dígitos.");
            return false;
        }
        if (titular.isEmpty() || !titular.matches("[a-zA-Z ]+")) {
            mostrarToast("Titular inválido", "El titular debe contener solo letras y espacios.");
            return false;
        }
        if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            mostrarToast("Fecha inválida", "Usa formato MM/YY");
            return false;
        }
        String[] partes = fecha.split("/");
        int mes = Integer.parseInt(partes[0]);
        int anio = Integer.parseInt("20" + partes[1]);
        Calendar hoy = Calendar.getInstance();
        int mesActual = hoy.get(Calendar.MONTH) + 1;
        int anioActual = hoy.get(Calendar.YEAR);
        if (anio < anioActual || (anio == anioActual && mes < mesActual)) {
            mostrarToast("Tarjeta vencida", "La tarjeta debe tener una fecha posterior a la actual.");
            return false;
        }
        if (codigo.isEmpty() || !codigo.matches("\\d{3,4}")) {
            mostrarToast("Código de seguridad inválido", "Debe tener entre 3 y 4 dígitos.");
            return false;
        }
        if (tipo.isEmpty()) {
            mostrarToast("Tipo de tarjeta", "Debe seleccionar el tipo de tarjeta.");
            return false;
        }
        return true;
    }

    private void mostrarToast(String titulo, String mensaje) {
        Toast.makeText(getContext(), titulo, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void detectarTipo(String bin) {
        requireActivity().runOnUiThread(() -> {
            String tipoDetectado = detectarTipoPorBin(bin);

            if (!tipoDetectado.equals("Desconocido")) {
                binding.TipoEditText.setText(tipoDetectado);
                switch (tipoDetectado) {
                    case "Visa":
                        binding.logoMarcaImageView.setImageResource(R.drawable.visa);
                        binding.logoMarcaImageView.setVisibility(View.VISIBLE);
                        break;
                    case "MasterCard":
                        binding.logoMarcaImageView.setImageResource(R.drawable.mastercard);
                        binding.logoMarcaImageView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        binding.logoMarcaImageView.setVisibility(View.GONE);
                        break;
                }
            } else {
                Log.e("BIN", "Tipo de tarjeta no reconocido para BIN: " + bin);
            }
        });
    }

    private String detectarTipoPorBin(String bin) {
        if (bin.startsWith("457173") || bin.startsWith("411111") || bin.startsWith("4")) {
            return "Visa";
        } else if (bin.startsWith("520082") || bin.startsWith("510510") || bin.startsWith("5")) {
            return "MasterCard";
        } else {
            return "Desconocido";
        }
    }

}