package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misraices.R;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentTarjetasBinding;
import com.example.misraices.view.adapter.AdapterTarjetaDetalle;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;


public class TarjetasFragment extends Fragment {
    private FragmentTarjetasBinding binding;
    private TarjetaViewModel tarjetaViewModel;
    private int usuarioId;

    public TarjetasFragment() {
        // Required empty public constructor
    }

    public static TarjetasFragment newInstance() {
        TarjetasFragment fragment = new TarjetasFragment();
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
        binding = FragmentTarjetasBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
        tarjetaViewModel.cargarTarjetas();
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
        getParentFragmentManager().setFragmentResultListener("recargar_tarjetas", this, (key, bundle) -> {
            tarjetaViewModel.cargarTarjetas();
        });
    }

    private void initListener() {
        tarjetaViewModel.obtenerTarjetas().observe(getViewLifecycleOwner(), tarjetas -> {
            if (tarjetas != null) {
                List<TarjetaCredito> tarjetasUsuario = new ArrayList<>();
                for (TarjetaCredito tarjeta : tarjetas) {
                    if (tarjeta.getUsuario() != null && tarjeta.getUsuario().getId() == usuarioId) {
                        tarjetasUsuario.add(tarjeta);
                    }
                }
                AdapterTarjetaDetalle adapter = new AdapterTarjetaDetalle(tarjetasUsuario, getContext(), this::abrirtarjetaDetalle);
                binding.recyclerViewTarjetas.setLayoutManager(
                        new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                );
                binding.recyclerViewTarjetas.setAdapter(adapter);
            }
        });
        binding.btnAgregar.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, NewTarjetaFragment.newInstance()).addToBackStack(null).commit();
        });
    }

    private void abrirtarjetaDetalle(TarjetaCredito tarjetaCredito) {
        TarjetaDetalleFragment fragment = new TarjetaDetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tarjeta", tarjetaCredito);
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();

    }

}