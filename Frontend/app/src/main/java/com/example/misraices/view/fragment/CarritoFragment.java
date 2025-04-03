package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misraices.R;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.FragmentCarritoBinding;
import com.example.misraices.view.adapter.AdapterPedido;
import com.example.misraices.viewModel.PedidoViewModel;

import java.util.ArrayList;
import java.util.List;


public class CarritoFragment extends Fragment {
    private FragmentCarritoBinding binding;
    private PedidoViewModel pedidoViewModel;

    public CarritoFragment() {
        // Required empty public constructor
    }


    public static CarritoFragment newInstance() {
        CarritoFragment fragment = new CarritoFragment();
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
        binding = FragmentCarritoBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);

    }

    private void initListener() {
        pedidoViewModel.getDetallesLiveData().observe(getViewLifecycleOwner(), detalles -> {
            if (detalles != null && !detalles.isEmpty()) {
                binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewProducto.setAdapter(new AdapterPedido(detalles, getContext(),pedidoViewModel));
                binding.totalTxt.setText(String.format("Total $ %.2f", pedidoViewModel.calcularTotal()));
            }else{
                binding.totalTxt.setText("Total $ 0.00");
            }
        });

    }
}