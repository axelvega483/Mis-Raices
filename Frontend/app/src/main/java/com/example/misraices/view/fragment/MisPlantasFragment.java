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
import com.example.misraices.data.SQLite.Data.AppDatabase;
import com.example.misraices.data.SQLite.Model.Planta;
import com.example.misraices.data.SQLite.ViewModel.MisPlantasViewModel;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentMisPlantasBinding;
import com.example.misraices.view.adapter.AdapterMisPlantas;
import com.example.misraices.view.adapter.AdapterPedido;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class MisPlantasFragment extends Fragment {
    private UsuarioViewModel usuarioViewModel;
    private MisPlantasViewModel misPlantasViewModel;
    private PedidoViewModel pedidoViewModel;
    FragmentMisPlantasBinding binding;
    private int usuarioId;

    public MisPlantasFragment() {
        // Required empty public constructor
    }


    public static MisPlantasFragment newInstance() {
        MisPlantasFragment fragment = new MisPlantasFragment();
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
        binding = FragmentMisPlantasBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
        misPlantasViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(MisPlantasViewModel.class);

        binding.recyclerViewMisPlantas.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void initListener() {
        misPlantasViewModel.getPlantas().observe(getViewLifecycleOwner(), plantas -> {
            if (plantas != null) {

                binding.recyclerViewMisPlantas.setAdapter(new AdapterMisPlantas(plantas));

            }
        });
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            pedidoViewModel.obtenerPedidos().observe(getViewLifecycleOwner(), pedidos -> {
                if (pedidos != null) {
                    misPlantasViewModel.sincronizarPlantasDesdePedidos(pedidos);
                }
            });
        });

    }

}
