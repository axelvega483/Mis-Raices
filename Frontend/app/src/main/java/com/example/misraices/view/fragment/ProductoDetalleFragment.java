package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.misraices.R;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.FragmentProductoDetalleBinding;
import com.example.misraices.viewModel.PedidoViewModel;

public class ProductoDetalleFragment extends Fragment {
    private FragmentProductoDetalleBinding binding;
    private PedidoViewModel pedidoViewModel;
    private boolean isImageExpanded = false;
    private boolean isInitialized = false;
    private int originalWidth;
    private int originalHeight;
    private float originalRadius;
    private float originalElevation;

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
            Producto producto = (Producto) getArguments().getSerializable("producto");
            if (producto != null) {
                binding.TituloDetalleTxt.setText(producto.getNombre());
                binding.descrDetalleTxt.setText(producto.getDescripcion());
                binding.precioDetalleTxt.setText(String.format("Precio: $ %.2f", producto.getPrecio()));
                binding.stockDetalleTxt.setText("Stock: " + producto.getStock());
                Glide.with(this).load(producto.getImg()).into(binding.imgProductoDetalle);
            } else {
                Log.e("DetalleProducto", "El producto recibido es null");
            }
        }
        binding.cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                originalWidth = binding.cardView.getWidth();
                originalHeight = binding.cardView.getHeight();
                originalRadius = binding.cardView.getRadius();
                originalElevation = binding.cardView.getCardElevation();
                isInitialized = true;

                // Remover listener
                binding.cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initListener() {
        binding.btnAgregarCarrito.setOnClickListener(v -> {
            Bundle args = getArguments();
            if (args != null) {
                Producto producto = (Producto) args.getSerializable("producto");
                Log.e("productoARGS", producto.toString());
                PedidoDetalle detalle = new PedidoDetalle(producto,producto.getNombre(),producto.getPrecio(),producto.getStock());
                if (!pedidoViewModel.existeProductoEnCarrito(producto.getId())) {
                    pedidoViewModel.cargarPedidosDetalles(detalle);
                    Toast.makeText(getContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "El producto ya está en el carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imgProductoDetalle.setOnClickListener(v -> {
            if (!isInitialized) return;
            ViewGroup.LayoutParams imgParams = binding.imgProductoDetalle.getLayoutParams();
            ViewGroup.LayoutParams cardParams = binding.cardView.getLayoutParams();

            if (isImageExpanded) {
                imgParams.width = originalWidth;
                imgParams.height = originalHeight;
                cardParams.width = originalWidth;
                cardParams.height = originalHeight;

                binding.imgProductoDetalle.setLayoutParams(imgParams);
                binding.cardView.setLayoutParams(cardParams);
                binding.cardView.setRadius(originalRadius);
                binding.cardView.setCardElevation(originalElevation);
                binding.imgProductoDetalle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                isImageExpanded = false;

            } else {
                imgParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                imgParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                cardParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                cardParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

                binding.imgProductoDetalle.setLayoutParams(imgParams);
                binding.cardView.setLayoutParams(cardParams);
                binding.cardView.setRadius(0f);
                binding.cardView.setCardElevation(0f);
                binding.cardView.setCardBackgroundColor(getResources().getColor(R.color.greenligth));
                binding.imgProductoDetalle.setScaleType(ImageView.ScaleType.FIT_CENTER);
                isImageExpanded = true;
            }
        });
    }
}