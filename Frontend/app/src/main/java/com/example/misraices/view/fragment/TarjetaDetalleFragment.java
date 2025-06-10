package com.example.misraices.view.fragment;

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
import com.example.misraices.databinding.FragmentTarjetaDetalleBinding;
import com.example.misraices.viewModel.TarjetaViewModel;

import java.util.Calendar;

public class TarjetaDetalleFragment extends Fragment {
    private FragmentTarjetaDetalleBinding binding;
    private TarjetaViewModel tarjetaViewModel;
    private TarjetaCredito tarjetaActual;

    public TarjetaDetalleFragment() {
        // Required empty public constructor
    }

    public static TarjetaDetalleFragment newInstance() {
        TarjetaDetalleFragment fragment = new TarjetaDetalleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTarjetaDetalleBinding.inflate(inflater, container, false);
        init();
        initListener();
        String[] tiposTarjeta = {"Visa", "MasterCard", "Naranja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                tiposTarjeta
        );
        binding.TipoEditText.setAdapter(adapter);
        return binding.getRoot();
    }

    private void init() {
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
        Bundle args = getArguments();
        if (args != null) {
            args.getSerializable("tarjeta");
            tarjetaActual = (TarjetaCredito) args.getSerializable("tarjeta");
            binding.numeroEditText.setText(tarjetaActual.getNumero());
            binding.titularEditText.setText(tarjetaActual.getTitular());
            binding.fechaEditText.setText(tarjetaActual.getFechaVencimiento());
            binding.TipoEditText.setText(tarjetaActual.getTipo());
            binding.codigoEditText.setText(tarjetaActual.getCodigoSeguridad());
        }
    }

    private void initListener() {
        binding.btnTarjetaEditar.setOnClickListener(view -> {
            if (tarjetaActual != null) {
                String numero = binding.numeroEditText.getText().toString().trim();
                String titular = binding.titularEditText.getText().toString().trim();
                String fecha = binding.fechaEditText.getText().toString().trim();
                String tipo = binding.TipoEditText.getText().toString().trim();
                String codigo = binding.codigoEditText.getText().toString().trim();

                if (numero.isEmpty() || !numero.matches("\\d{13,19}")) {
                    Toast.makeText(getContext(), "Número de tarjeta inválido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (titular.isEmpty() || !titular.matches("[a-zA-Z ]+")) {
                    Toast.makeText(getContext(), "Titular inválido", Toast.LENGTH_SHORT).show();
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
                    return;
                }

                if (codigo.isEmpty() || !codigo.matches("\\d{3,4}")) {
                    Toast.makeText(getContext(), "Código de seguridad inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tipo.isEmpty()) {
                    Toast.makeText(getContext(), "Debe ingresar el tipo de tarjeta", Toast.LENGTH_SHORT).show();
                    return;
                }

                tarjetaActual.setNumero(numero);
                tarjetaActual.setTitular(titular);
                tarjetaActual.setFechaVencimiento(fecha);
                tarjetaActual.setTipo(tipo);
                tarjetaActual.setCodigoSeguridad(codigo);

                tarjetaViewModel.editarTarjeta(tarjetaActual.getId(), tarjetaActual).observe(getViewLifecycleOwner(), result -> {
                    if (result.getData() != null) {
                        Toast.makeText(getContext(), "Tarjeta actualizada correctamente", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "Error al actualizar la tarjeta", Toast.LENGTH_SHORT).show();
                        Log.e("EditarTarjeta", "Error: " + result.isExito());
                    }
                });
            }
        });

        binding.btnDeleteTarjeta.setOnClickListener(view -> {
            tarjetaViewModel.eliminarTarjeta(tarjetaActual.getId()).observe(getViewLifecycleOwner(), result -> {
                Toast.makeText(getContext(), "Tarjeta eliminada correctamente", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }
}