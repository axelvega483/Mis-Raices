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
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.databinding.FragmentPedidoRealizadoBinding;
import com.example.misraices.view.adapter.AdapterPedido;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PedidoRealizadoFragment extends Fragment {
    private FragmentPedidoRealizadoBinding binding;
    private PedidoViewModel pedidoViewModel;
    private UsuarioViewModel usuarioViewModel;
    private int usuarioId;

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
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
    }

    private void initListener() {
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            pedidoViewModel.obtenerPedidos().observe(getViewLifecycleOwner(), pedidos -> {
                if (pedidos != null) {
                    List<Pedido> pedidosEnPreparacion = new ArrayList<>();

                    for (Pedido pedido : pedidos) {
                        if ("EN PREPARACIÓN".equals(pedido.getEstado())) {
                            if (pedido.getUsuario().getId() == usuario.getData().getId()) {
                                pedidosEnPreparacion.add(pedido);
                            }
                        }
                    }
                    if (!pedidosEnPreparacion.isEmpty()) {
                        binding.recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        binding.recyclerViewPedidos.setAdapter(new AdapterPedido(pedidosEnPreparacion, getContext(), this::mostrarPedido));
                        pedidoViewModel.setPedidoMutableLiveData(pedidosEnPreparacion.get(0));
                    }
                }
            });
        });
    }

    private void mostrarPedido(Pedido pedido) {
        PedidoDetalleFragment fragment = new PedidoDetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pedido", pedido);
        bundle.putInt("id", pedido.getId());
        bundle.putString("fecha", pedido.getFechaPedido());
        bundle.putSerializable("detalle",(Serializable) pedido.getDetalle());
        bundle.putDouble("total", pedido.getTotal());
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}