package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.misraices.databinding.FragmentProductoDetalleBinding;

public class ProductoDetalleFragment extends Fragment {
    private FragmentProductoDetalleBinding binding;

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
    }

}