package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misraices.R;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.databinding.FragmentPedidoRealizadoBinding;
import com.example.misraices.view.adapter.AdapterPedido;
import com.example.misraices.viewModel.PedidoViewModel;

import java.util.ArrayList;
import java.util.List;

public class PedidoRealizadoFragment extends Fragment {
    private FragmentPedidoRealizadoBinding binding;
    private PedidoViewModel pedidoViewModel;

    public PedidoRealizadoFragment() {
        // Required empty public constructor
    }


    public static PedidoRealizadoFragment newInstance() {
        PedidoRealizadoFragment fragment = new PedidoRealizadoFragment();
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
        binding = FragmentPedidoRealizadoBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
    }

    private void initListener() {
        pedidoViewModel.obtenerPedidos().observe(getViewLifecycleOwner(), pedidos -> {
            if (pedidos != null) {
                List<Pedido> pedidosEnCamino = new ArrayList<>();

                for (Pedido pedido : pedidos) {
                    if ("EN CAMINO".equals(pedido.getEstado())) {
                        pedidosEnCamino.add(pedido);
                    }
                }
                if (!pedidosEnCamino.isEmpty()) {
                    binding.recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                    binding.recyclerViewPedidos.setAdapter(new AdapterPedido(pedidosEnCamino, getContext()));
                    pedidoViewModel.setPedidoMutableLiveData(pedidosEnCamino.get(0));
                }
            }
        });

    }
}