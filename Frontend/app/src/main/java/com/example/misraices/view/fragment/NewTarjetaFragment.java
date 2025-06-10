package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentNewTarjetaBinding;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.Calendar;


public class NewTarjetaFragment extends Fragment {
    private FragmentNewTarjetaBinding binding;
    private TarjetaViewModel tarjetaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;

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

        String[] tiposTarjeta = {"Visa", "MasterCard", "Naranja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                tiposTarjeta
        );
        binding.TipoEditText.setAdapter(adapter);
    }

    private void initListener() {
        binding.btnNuevaTarjeta.setOnClickListener(view -> {
            usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
                String numero = binding.numeroEditText.getText().toString().trim();
                String titular = binding.titularEditText.getText().toString().trim();
                String fecha = binding.fechaEditText.getText().toString().trim();
                String tipo = binding.TipoEditText.getText().toString().trim();
                String codigo = binding.codigoEditText.getText().toString().trim();

                if (numero.isEmpty() || !numero.matches("\\d{13,19}")) {
                    Toast.makeText(getContext(), "Número de tarjeta inválido", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"El número de tarjeta debe tener entre 13 y 19 dígitos.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (titular.isEmpty() || !titular.matches("[a-zA-Z ]+")) {
                    Toast.makeText(getContext(), "Titular inválido", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"El titular debe contener solo letras y espacios.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                    Toast.makeText(getContext(), "Fecha inválida. Usa formato MM/YY", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] partes = fecha.split("/");
                int mes = Integer.parseInt(partes[0]);
                int anio = Integer.parseInt("20" + partes[1]);
                Calendar hoy = Calendar.getInstance();
                int mesActual = hoy.get(Calendar.MONTH) + 1;
                int anioActual = hoy.get(Calendar.YEAR);
                if (anio < anioActual || (anio == anioActual && mes < mesActual)) {
                    Toast.makeText(getContext(), "La tarjeta está vencida", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"La tarjeta debe tener una fecha de vencimiento posterior a la fecha actual.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (codigo.isEmpty() || !codigo.matches("\\d{3,4}")) {
                    Toast.makeText(getContext(), "Código de seguridad inválido", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"El código de seguridad debe tener entre 3 y 4 dígitos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tipo.isEmpty()) {
                    Toast.makeText(getContext(), "Debe ingresar el tipo de tarjeta", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),"Debe seleccionar el tipo de tarjeta.", Toast.LENGTH_SHORT).show();
                    return;
                }

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

                requireActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }

}