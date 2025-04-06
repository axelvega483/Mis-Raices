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

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentNewTarjetaBinding;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;


public class NewTarjetaFragment extends Fragment {
    private FragmentNewTarjetaBinding binding;
    private TarjetaViewModel tarjetaViewModel;
    private UsuarioViewModel usuarioViewModel;

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
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
    }

    private void initListener() {
        binding.btnNuevaTarjeta.setOnClickListener(view -> {
            SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
            int usuarioId = prefs.getInt("usuarioId", -1);
            usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(),usuario-> {
                Log.e("usuario", usuario.toString());
                TarjetaCredito tarjeta = new TarjetaCredito();
                tarjeta.setUsuario(usuario.getData());
                tarjeta.setNumero(binding.numeroEditText.getText().toString());
                tarjeta.setTitular(binding.titularEditText.getText().toString());
                tarjeta.setFechaVencimiento(binding.fechaEditText.getText().toString());
                tarjeta.setTipo(binding.TipoEditText.getText().toString());
                tarjeta.setCodigoSeguridad(binding.codigoEditText.getText().toString());
                tarjetaViewModel.crearTarjeta(tarjeta);
                Log.e("tarjeta creada", tarjeta.toString());
                tarjetaViewModel.setTarjetaLiveData(tarjeta);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, TarjetasFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            });

        });

    }
}