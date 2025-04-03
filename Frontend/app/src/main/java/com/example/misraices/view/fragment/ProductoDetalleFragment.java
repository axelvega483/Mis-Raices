package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.FragmentProductoDetalleBinding;
import com.example.misraices.viewModel.PedidoViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoDetalleFragment extends Fragment {
    private FragmentProductoDetalleBinding binding;
    private PedidoViewModel pedidoViewModel;

    public ProductoDetalleFragment() {
        // Required empty public constructor
    }

    public static ProductoDetalleFragment newInstance() {
        ProductoDetalleFragment fragment = new ProductoDetalleFragment();
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
        binding = FragmentProductoDetalleBinding.inflate(inflater, container, false);
        init();
        initListener();
        return binding.getRoot();
    }

    private void init() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            binding.TituloDetalleTxt.setText(args.getString("nombre"));
            binding.descrDetalleTxt.setText("Descripcíon: " + args.getString("descripcion"));
            binding.precioDetalleTxt.setText("Precio: " + String.format("$ %.2f", args.getDouble("precio")));
            binding.stockDetalleTxt.setText("Stock: " + args.getInt("stock"));
            Glide.with(this)
                    .load(args.getString("imagen"))
                    .into(binding.imgProductoDetalle);

        }


    }

    private void initListener() {
        binding.btnAgregarCarrito.setOnClickListener(v -> {
            Bundle args = getArguments();
            if (args != null) {
                Producto producto = (Producto) args.getSerializable("producto");
                Log.e("productoARGS", producto.toString());
                PedidoDetalle detalle = new PedidoDetalle(producto, args.getString("nombre"), args.getDouble("precio"), args.getInt("stock"));
                if (!pedidoViewModel.existeProductoEnCarrito(producto.getId())) {
                    pedidoViewModel.cargarPedidosDetalles(detalle);
                    Toast.makeText(getContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "El producto ya está en el carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}