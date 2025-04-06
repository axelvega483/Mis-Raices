package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentNewTarjetaBinding;
import com.example.misraices.viewModel.TarjetaViewModel;


public class NewTarjetaFragment extends Fragment {
    private FragmentNewTarjetaBinding binding;
    private TarjetaViewModel tarjetaViewModel;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewTarjetaBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
    }

    private void initListener() {
        binding.btnNuevaTarjeta.setOnClickListener(view -> {
           TarjetaCredito tarjeta = new TarjetaCredito();
            tarjeta.setNumero(binding.numeroEditText.getText().toString());
            tarjeta.setTitular(binding.titularEditText.getText().toString());
            tarjeta.setFechaVencimiento(binding.fechaEditText.getText().toString());
            tarjeta.setTipo(binding.TipoEditText.getText().toString());
            tarjeta.setCodigoSeguridad(binding.codigoEditText.getText().toString());
            tarjetaViewModel.crearTarjeta(tarjeta);
            tarjetaViewModel.setTarjetaLiveData(tarjeta);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, TarjetasFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

    }
}