package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.misraices.R;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.util.EstadoPedido;
import com.example.misraices.databinding.FragmentPedidoDetalleBinding;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.TarjetaViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PedidoDetalleFragment extends Fragment {
    private FragmentPedidoDetalleBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private PedidoViewModel pedidoViewModel;
    private int usuarioId;
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PedidoDetalleFragment() {
        // Required empty public constructor
    }


    public static PedidoDetalleFragment newInstance() {
        PedidoDetalleFragment fragment = new PedidoDetalleFragment();
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
        binding = FragmentPedidoDetalleBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.direccionTxt.setText("Dirección: " + usuario.getData().getDireccion().getCalle() + " " + usuario.getData().getDireccion().getNumero());
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            String estado = args.getString("estado");
            if (estado != null && estado.equals(EstadoPedido.CANCELADO.name())) {
                binding.btnCancelarPedido.setVisibility(View.GONE);
            } else {
                binding.btnCancelarPedido.setVisibility(View.VISIBLE);
            }
            String fechaStr = args.getString("fecha");
            if (fechaStr != null) {
                // Suponiendo que fechaStr viene en formato ISO_LOCAL_DATE_TIME
                LocalDateTime fecha = LocalDateTime.parse(fechaStr);
                binding.fechaTxt.setText(outputFormatter.format(fecha));
            }
            binding.pedidoEstado.setText("Estado: " + args.getString("estado"));
            binding.pedidoIDTxt.setText(String.valueOf("Pedido N° " + args.getInt("id")));
            binding.totalTxt.setText(String.format("$ %.2f", args.getDouble("total")));
            List<PedidoDetalle> detalles = (List<PedidoDetalle>) args.getSerializable("detalle");
            for (PedidoDetalle detalle : detalles) {
                TableRow row = new TableRow(getContext());

                TextView producto = new TextView(getContext());
                producto.setText(detalle.getProducto().getNombre());
                producto.setPadding(8, 8, 8, 8);
                producto.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                TextView cantidad = new TextView(getContext());
                cantidad.setText(String.valueOf(detalle.getCantidad()));
                cantidad.setPadding(8, 8, 8, 8);
                cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                TextView precio = new TextView(getContext());
                precio.setText(String.format("$ %.2f", detalle.getProducto().getPrecio()));
                precio.setPadding(8, 8, 8, 8);
                precio.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                row.addView(producto);
                row.addView(cantidad);
                row.addView(precio);

                binding.tableLayout.addView(row);
            }

        }
    }

    private void initListener() {
        binding.btnCancelarPedido.setOnClickListener(v -> {
            Bundle args = getArguments();
            if (args != null) {
                if (!args.getString("estado").equals(EstadoPedido.CANCELADO)) {
                    pedidoViewModel.cancelarPedido(args.getInt("id"));
                    getFragmentManager().popBackStack();
                }
            }
        });
    }
}


