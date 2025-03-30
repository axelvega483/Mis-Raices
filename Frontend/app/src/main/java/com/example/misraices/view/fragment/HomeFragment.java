package com.example.misraices.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misraices.data.model.Categoria;
import com.example.misraices.data.model.Producto;
import com.example.misraices.databinding.FragmentHomeBinding;
import com.example.misraices.view.adapter.AdaptadorCategorias;
import com.example.misraices.view.adapter.AdaptadorProductos;
import com.example.misraices.viewModel.CategoriaViewModel;
import com.example.misraices.viewModel.ProductoViewModel;

import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductoViewModel productoViewModel;
    private CategoriaViewModel categoriaViewModel;

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

    public void initlistener() {


        productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewProducto.setAdapter(new AdaptadorProductos((List<Producto>) result, getContext()));
                productoViewModel.setProductoMutableLiveData(result);

            }
        });


        categoriaViewModel.obtenerCategorias().observe(getViewLifecycleOwner(), categorias -> {
            if (categorias != null) {
                binding.recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewCategoria.setAdapter(new AdaptadorCategorias(categorias, getContext(), categoria -> {
                    mostrarProductosPorCategoria(categoria);
                }));
                categoriaViewModel.setCategoriaMutableLiveData(categorias);
            }
        });
        binding.seeAllTxt.setOnClickListener(v -> {
            productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), result -> {
                if (result != null) {
                    binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                    binding.recyclerViewProducto.setAdapter(new AdaptadorProductos((List<Producto>) result, getContext()));
                    productoViewModel.setProductoMutableLiveData(result);

                }
            });
        });

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrarProductos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    cargarTodosLosProductos();
                } else {
                    filtrarProductos(newText);
                }
                return false;
            }
        });

    }

    private void mostrarProductosPorCategoria(Categoria categoria) {
        productoViewModel.obtenerProductosPorCategoria(categoria.getId()).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) {
                binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewProducto.setAdapter(new AdaptadorProductos((List<Producto>) productos, getContext()));
            }
        });
    }

    private void filtrarProductos(String query) {
        productoViewModel.obtenerProductosPorNombre(query).observe(getViewLifecycleOwner(), producto -> {
            Log.e("productoXnombre", producto.toString()  );
            if (producto != null) {
                binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewProducto.setAdapter(new AdaptadorProductos(producto, getContext()));
            } else {
                Toast.makeText(getContext(), "No se encontraron productos que coincidan con: " + query, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void cargarTodosLosProductos(){
        productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewProducto.setAdapter(new AdaptadorProductos((List<Producto>) result, getContext()));
                productoViewModel.setProductoMutableLiveData(result);

            }
        });
    }
}