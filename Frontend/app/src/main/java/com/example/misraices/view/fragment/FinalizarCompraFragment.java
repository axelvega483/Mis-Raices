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
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.databinding.FragmentFinalizarCompraBinding;
import com.example.misraices.view.adapter.AdapterTarjetaCompra;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;


public class FinalizarCompraFragment extends Fragment {
    private FragmentFinalizarCompraBinding binding;
    private PedidoViewModel pedidoViewModel;
    private TarjetaViewModel tarjetaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private TarjetaCredito tarjetaSeleccionada;
    private int usuarioId;

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

        usuarioViewModel.getDireccionActualizada().observe(getViewLifecycleOwner(), direccionActualizada -> {
            if (direccionActualizada != null && direccionActualizada) {
                binding.btnfinalizarCompra.performClick();
                usuarioViewModel.setDireccionActualizada(false);
            }
        });

        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
    }

    private void initListener() {

        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            tarjetaViewModel.obtenerTarjetas().observe(getViewLifecycleOwner(), tarjetas -> {
                if (tarjetas != null && !tarjetas.isEmpty()) {
                    List<TarjetaCredito> tarjetasUsuario = new ArrayList<>();
                    for (TarjetaCredito tarjeta : tarjetas) {
                        Log.e("tarjeta", tarjeta.toString());
                        if (tarjeta.getUsuario() != null && tarjeta.getUsuario().getId() == usuario.getData().getId()) {
                            tarjetasUsuario.add(tarjeta);
                        }
                    }

                    AdapterTarjetaCompra adapter = new AdapterTarjetaCompra(tarjetasUsuario, getContext(), tarjeta -> {
                        tarjetaSeleccionada = tarjeta;
                        Toast.makeText(getContext(), "Seleccionaste la tarjeta: " + tarjeta.getNumero(), Toast.LENGTH_SHORT).show();
                    });

                    binding.recyclerViewTarjetas.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerViewTarjetas.setAdapter(adapter);
                }
            });
        });
        binding.btnfinalizarCompra.setOnClickListener(view -> {
            if (!validarDatosIniciales()) return;
            procesarUsuarioYCrearPedido();
        });
        binding.btnAgregarTarjeta.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, NewTarjetaFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });


    }

    private boolean validarDatosIniciales() {
        if (tarjetaSeleccionada == null) {
            Toast.makeText(getContext(), "Seleccione una tarjeta", Toast.LENGTH_SHORT).show();
            return false;
        }

        List<PedidoDetalle> detalles = pedidoViewModel.getDetallesLiveData().getValue();
        if (detalles == null || detalles.isEmpty()) {
            Toast.makeText(getContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void procesarUsuarioYCrearPedido() {
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario.getData() == null) {
                Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuario.getData().getDireccion() == null) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, MapaFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getContext(), "Por favor, ingrese su dirección antes de continuar.", Toast.LENGTH_LONG).show();
                return;
            }

            List<PedidoDetalle> detalles = pedidoViewModel.getDetallesLiveData().getValue();
            Pedido pedido = new Pedido();
            pedido.setDetalle(detalles);
            pedido.setUsuario(usuario.getData());
            pedido.setEstado("PENDIENTE");

            pedidoViewModel.crearPedido(pedido).observe(getViewLifecycleOwner(), resultado -> {
                Pedido pedidoCreado = resultado != null ? resultado.getData() : null;
                    Log.e("pedidoCreado",resultado.getData().toString());
                if (pedidoCreado == null || pedidoCreado.getId() == null) {
                    Toast.makeText(getContext(), "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Ahora tenemos el pedido con ID asignado desde el backend
                pedidoViewModel.setPedidoMutableLiveData(pedidoCreado);
                pedidoViewModel.limpiarCarrito();

                finalizarPedidoSiEsVálido(pedidoCreado.getId());
            });

        });
    }

    private void finalizarPedidoSiEsVálido(int pedidoId) {
        pedidoViewModel.obtenerPedidoPorId(pedidoId).observe(getViewLifecycleOwner(), response -> {
            Pedido pedido = response != null ? response.getData() : null;

            if (pedido == null) {
                Toast.makeText(getContext(), "No se pudo obtener el pedido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tarjetaSeleccionada.getSaldo() >= pedido.getTotal()
                    && "PENDIENTE".equals(pedido.getEstado())) {

                pedidoViewModel.finalizarCompra(pedido.getId(), tarjetaSeleccionada.getId());
                Toast.makeText(getContext(), "Compra finalizada", Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, PedidoRealizadoFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Saldo insuficiente o pedido inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

}