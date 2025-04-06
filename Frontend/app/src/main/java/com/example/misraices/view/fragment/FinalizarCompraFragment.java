package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentFinalizarCompraBinding;
import com.example.misraices.view.adapter.AdapterTarjetaCompra;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;


public class FinalizarCompraFragment extends Fragment {
    private FragmentFinalizarCompraBinding binding;
    private PedidoViewModel pedidoViewModel;
    private TarjetaViewModel tarjetaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private TarjetaCredito tarjetaSeleccionada;

    public FinalizarCompraFragment() {
        // Required empty public constructor
    }


    public static FinalizarCompraFragment newInstance() {
        FinalizarCompraFragment fragment = new FinalizarCompraFragment();
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
        binding = FragmentFinalizarCompraBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
    }

    private void initListener() {
        tarjetaViewModel.obtenerTarjetas().observe(getViewLifecycleOwner(), tarjetas -> {
            if (tarjetas != null && !tarjetas.isEmpty()) {
                AdapterTarjetaCompra adapter = new AdapterTarjetaCompra(tarjetas, getContext(), tarjeta -> {
                    tarjetaSeleccionada = tarjeta;
                    Toast.makeText(getContext(), "Seleccionaste la tarjeta: " + tarjeta.getNumero(), Toast.LENGTH_SHORT).show();
                });
                binding.recyclerViewTarjetas.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewTarjetas.setAdapter(adapter);
            }
        });


        binding.btnfinalizarCompra.setOnClickListener(view -> {
            if (tarjetaSeleccionada == null) {
                Toast.makeText(getContext(), "Seleccione una tarjeta", Toast.LENGTH_SHORT).show();
                return;
            }

            usuarioViewModel.obtenerUsuario().observe(getViewLifecycleOwner(), usuarios -> {
                if (usuarios != null && !usuarios.isEmpty()) {
                    Usuario user = usuarios.get(0); // suponiendo que es el usuario logueado

                    Pedido pedido = new Pedido();
                    pedido.setDetalle(pedidoViewModel.getDetallesLiveData().getValue());
                    pedido.setUsuario(user);
                    pedidoViewModel.crearPedido(pedido);

                    new android.os.Handler().postDelayed(() -> {
                        pedidoViewModel.obtenerPedidos().observe(getViewLifecycleOwner(), pedidos -> {
                            if (pedidos != null && !pedidos.isEmpty()) {
                                Pedido ultimoPedido = pedidos.get(pedidos.size() - 1);

                                if (tarjetaSeleccionada.getSaldo() >= ultimoPedido.getTotal()) {
                                    if (ultimoPedido.getEstado().equals("EN PREPARACIÓN")) {
                                        pedidoViewModel.finalizarCompra(ultimoPedido.getId(), tarjetaSeleccionada.getId());
                                        Toast.makeText(getContext(), "Compra finalizada", Toast.LENGTH_SHORT).show();

                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.frameContainer, PedidoRealizadoFragment.newInstance())
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }, 1500);

                    pedidoViewModel.limpiarCarrito();
                    pedidoViewModel.setPedidoMutableLiveData(pedido);
                }
            });

        });
    }
}