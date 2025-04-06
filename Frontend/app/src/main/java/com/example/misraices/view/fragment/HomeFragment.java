package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.Categoria;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.FragmentHomeBinding;
import com.example.misraices.view.adapter.AdaptadorCategorias;
import com.example.misraices.view.adapter.AdaptadorProductos;
import com.example.misraices.viewModel.CategoriaViewModel;
import com.example.misraices.viewModel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductoViewModel productoViewModel;
    private CategoriaViewModel categoriaViewModel;
    private Handler handler = new Handler();
    private Runnable searchRunnable;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();
        initlistener();
        return binding.getRoot();
    }

    public void init() {
        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
        categoriaViewModel = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);

    }
    private void initlistener() {
        productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) {
                productoViewModel.setProductoMutableLiveData(productos);
                mostrarProductos(productos, true); // solo productos con stock
            }
        });

        categoriaViewModel.obtenerCategorias().observe(getViewLifecycleOwner(), categorias -> {
            if (categorias != null) {
                categoriaViewModel.setCategoriaMutableLiveData(categorias);
                binding.recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewCategoria.setAdapter(new AdaptadorCategorias(categorias, requireContext(), this::mostrarProductosPorCategoria));
            }
        });

        binding.seeAllTxt.setOnClickListener(v -> {
            List<Producto> productos = productoViewModel.obtenerProductos().getValue();
            if (productos != null) mostrarProductos(productos, true);
        });

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrarProductos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeCallbacks(searchRunnable);
                searchRunnable = () -> {
                    if (newText.isEmpty()) {
                        cargarTodosLosProductos();
                    } else {
                        filtrarProductos(newText);
                    }
                };
                handler.postDelayed(searchRunnable, 300);
                return true;
            }
        });
    }

    private void mostrarProductos(List<Producto> productos, boolean filtrarStock) {
        List<Producto> listaFiltrada = new ArrayList<>();
        for (Producto p : productos) {
            if (!filtrarStock || p.getStock() > 0) listaFiltrada.add(p);
        }
        binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewProducto.setAdapter(new AdaptadorProductos(listaFiltrada, requireContext(), this::abrirDetalleProducto));
    }

    private void mostrarProductosPorCategoria(Categoria categoria) {
        productoViewModel.obtenerProductosPorCategoria(categoria.getId()).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) mostrarProductos(productos, true); // con control de stock
        });
    }

    private void filtrarProductos(String query) {
        productoViewModel.obtenerProductosPorNombre(query).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null && !productos.isEmpty()) {
                mostrarProductos(productos, false); // se muestran todos, incluso sin stock
            } else {
                Toast.makeText(requireContext(), "No se encontraron productos para: " + query, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarTodosLosProductos() {
        List<Producto> productos = productoViewModel.obtenerProductos().getValue();
        if (productos != null) mostrarProductos(productos, true);
    }

    private void abrirDetalleProducto(Producto producto) {
        ProductoDetalleFragment fragment = new ProductoDetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("producto", producto);
        bundle.putString("nombre", producto.getNombre());
        bundle.putString("descripcion", producto.getDescripcion());
        bundle.putDouble("precio", producto.getPrecio());
        bundle.putInt("stock", producto.getStock());
        bundle.putString("imagen", producto.getImg());
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}