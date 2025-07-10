package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

    public static FinalizarCompraFragment newInstance() {
        return new FinalizarCompraFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFinalizarCompraBinding.inflate(inflater, container, false);
        initViewModels();
        initListeners();
        cargarDatosUsuario();
        return binding.getRoot();
    }

    private void initViewModels() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
        tarjetaViewModel = new ViewModelProvider(requireActivity()).get(TarjetaViewModel.class);
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);

        tarjetaViewModel.cargarTarjetas();

        getParentFragmentManager().setFragmentResultListener("recargar_tarjetas", this, (key, bundle) -> tarjetaViewModel.cargarTarjetas());

        usuarioViewModel.getDireccionActualizada().observe(getViewLifecycleOwner(), actualizada -> {
            if (Boolean.TRUE.equals(actualizada)) {
                binding.btnfinalizarCompra.performClick();
                usuarioViewModel.setDireccionActualizada(false);
            }
        });
    }

    private void initListeners() {
        binding.btnfinalizarCompra.setOnClickListener(view -> {
            if (validarDatosIniciales()) {
                procesarPedido();
            }
        });

        binding.btnAgregarTarjeta.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, NewTarjetaFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void cargarDatosUsuario() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario.getData() == null) return;

            tarjetaViewModel.obtenerTarjetas().observe(getViewLifecycleOwner(), tarjetas -> {
                if (tarjetas == null) return;

                List<TarjetaCredito> tarjetasUsuario = new ArrayList<>();
                for (TarjetaCredito tarjeta : tarjetas) {
                    if (tarjeta.getUsuario() != null
                            && tarjeta.getUsuario().getId() == usuario.getData().getId()
                            && tarjeta.getId() != null) {
                        tarjetasUsuario.add(tarjeta);
                    }
                }

                final int idUltimaTarjetaUsada = prefs.getInt("ultima_tarjeta_id", -1);

                boolean idValido = tarjetasUsuario.stream()
                        .anyMatch(t -> t.getId() != null && t.getId() == idUltimaTarjetaUsada);

                if (!idValido) {
                    tarjetaSeleccionada = null;
                } else {
                    for (TarjetaCredito t : tarjetasUsuario) {
                        if (t.getId() != null && t.getId() == idUltimaTarjetaUsada) {
                            tarjetaSeleccionada = t;
                            break;
                        }
                    }
                }

                AdapterTarjetaCompra adapter = new AdapterTarjetaCompra(
                        tarjetasUsuario, getContext(), tarjeta -> {
                    tarjetaSeleccionada = tarjeta;
                    prefs.edit().putInt("ultima_tarjeta_id", tarjeta.getId()).apply();
                    Toast.makeText(getContext(), "Seleccionaste la tarjeta: " + tarjeta.getNumero(), Toast.LENGTH_SHORT).show();
                }, idUltimaTarjetaUsada);

                binding.recyclerViewTarjetas.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewTarjetas.setAdapter(adapter);
                binding.btnfinalizarCompra.setVisibility(tarjetasUsuario.isEmpty() ? View.GONE : View.VISIBLE);
            });
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

    private void procesarPedido() {
        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario.getData() == null) {
                Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuario.getData().getDireccion() == null) {
                Toast.makeText(getContext(), "Ingrese su dirección antes de continuar", Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, MapaFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return;
            }

            List<PedidoDetalle> detalles = pedidoViewModel.getDetallesLiveData().getValue();
            double total = detalles.stream().mapToDouble(PedidoDetalle::getSubtotal).sum();

            if (tarjetaSeleccionada.getSaldo() < total) {
                Toast.makeText(getContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                return;
            }

            Pedido pedido = new Pedido();
            pedido.setDetalle(detalles);
            pedido.setUsuario(usuario.getData());

            pedidoViewModel.crearPedido(pedido).observe(getViewLifecycleOwner(), resultado -> {
                if (resultado == null || resultado.getData() == null) {
                    Toast.makeText(getContext(), "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Pedido pedidoCreado = resultado.getData();
                pedidoViewModel.setPedidoMutableLiveData(pedidoCreado);
                pedidoViewModel.limpiarCarrito();

                finalizarCompra(pedidoCreado.getId());
            });
        });
    }

    private void finalizarCompra(int pedidoId) {
        pedidoViewModel.obtenerPedidoPorId(pedidoId).observe(getViewLifecycleOwner(), response -> {
            Pedido pedido = response != null ? response.getData() : null;
            if (pedido == null) {
                Toast.makeText(getContext(), "No se pudo obtener el pedido", Toast.LENGTH_SHORT).show();
                return;
            }

            pedidoViewModel.finalizarCompra(pedido.getId(), tarjetaSeleccionada.getId());
            Toast.makeText(getContext(), "Compra finalizada", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, PedidoRealizadoFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
