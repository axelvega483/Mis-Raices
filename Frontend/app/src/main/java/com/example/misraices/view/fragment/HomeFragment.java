package com.example.misraices.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.misraices.R;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Categoria;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.databinding.FragmentHomeBinding;
import com.example.misraices.view.adapter.AdaptadorCategorias;
import com.example.misraices.view.adapter.AdaptadorProductos;
import com.example.misraices.viewModel.CategoriaViewModel;
import com.example.misraices.viewModel.ProductoViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductoViewModel productoViewModel;
    private CategoriaViewModel categoriaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Handler handler = new Handler();
    private Runnable searchRunnable;
    private int usuarioId;
    private List<Producto> listaFiltrada = new ArrayList<>();
    private AdaptadorProductos adaptadorProductos;

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
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
    }

    private void initlistener() {
        usuarioViewModel.getDireccionActualizada().observe(getViewLifecycleOwner(), actualizada -> {
            if (actualizada != null && actualizada) {
                usuarioViewModel.setDireccionActualizada(false);
                Log.e("entra", "Actualizando dirección");

            }
        });

        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), result -> {
            if (result != null && result.getData() != null) {
                Usuario usuario = result.getData();
                Direccion direccion = usuario.getDireccion();
                if (direccion != null) {
                    String calle = direccion.getCalle() != null ? direccion.getCalle() : "";
                    Long numero = direccion.getNumero() != null ? direccion.getNumero() : 0L;
                    String direccionTexto = (calle + " " + numero).trim();
                    if (direccionTexto.isEmpty()) {
                        direccionTexto = "Dirección";
                    }
                    binding.mapDireccionUsuario.setText(direccionTexto);
                } else {
                    binding.mapDireccionUsuario.setText("Dirección");
                }
            } else {
                binding.mapDireccionUsuario.setText("Dirección");
            }
        });
        binding.mapDireccionUsuario.setOnClickListener(v -> {
            MapaFragment mapaFragment = new MapaFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, mapaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) {
                productoViewModel.setProductoMutableLiveData(productos.getData());
                mostrarProductos(productos.getData(), true);
            }
        });

        categoriaViewModel.obtenerCategorias().observe(getViewLifecycleOwner(), categorias -> {
            if (categorias != null) {
                categoriaViewModel.setCategoriaMutableLiveData(categorias);
                binding.recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewCategoria.setAdapter(new AdaptadorCategorias(categorias, requireContext(), this::mostrarProductosPorCategoria));
            }
        });

        binding.verTodoTxt.setOnClickListener(v -> {
            productoViewModel.obtenerProductos().observe(getViewLifecycleOwner(), productos -> {
                if (productos != null) {
                    productoViewModel.setProductoMutableLiveData(productos.getData());
                    mostrarProductos(productos.getData(), true);
                }
            });

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
        binding.btnFiltroOrden.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), v);
            popup.getMenuInflater().inflate(R.menu.orden_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.orden_alfabetico) {
                    ordenarAlfabeticamente();
                    binding.btnFiltroOrden.setText("A-Z");
                } else if (itemId == R.id.orden_precio_menor_mayor) {
                    ordenarPrecioMenorMayor();
                    binding.btnFiltroOrden.setText("Precio ⬆");

                } else if (itemId == R.id.orden_precio_mayor_menor) {
                    ordenarPrecioMayorMenor();
                    binding.btnFiltroOrden.setText("Precio ⬇");
                }
                return true;
            });

            popup.show();
        });

    }

    private void mostrarProductos(List<Producto> productos, boolean filtrarStock) {
        listaFiltrada.clear();
        for (Producto p : productos) {
            if (!filtrarStock || p.getStock() > 0) {
                listaFiltrada.add(p);
            }
        }
        if (adaptadorProductos == null) {
            adaptadorProductos = new AdaptadorProductos(listaFiltrada, requireContext(), this::abrirDetalleProducto);
            binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerViewProducto.setAdapter(adaptadorProductos);
        } else {
            adaptadorProductos.notifyDataSetChanged();
        }
    }

    private void mostrarProductosPorCategoria(Categoria categoria) {
        productoViewModel.obtenerProductosPorCategoria(categoria.getId()).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) mostrarProductos(productos.getData(), true);
        });
    }

    private void filtrarProductos(String query) {
        productoViewModel.obtenerProductosPorNombre(query).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null && !productos.isExito()) {
                mostrarProductos(productos.getData(), true);
            } else {
                Toast.makeText(requireContext(), "No se encontraron productos para: " + query, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarTodosLosProductos() {
        ApiRespo<List<Producto>> respuesta = productoViewModel.obtenerProductos().getValue();
        if (respuesta != null && respuesta.isExito() && respuesta.getData() != null) {
            mostrarProductos(respuesta.getData(), true);
        } else {
            mostrarProductos(Collections.emptyList(), false);
        }
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

    private void ordenarAlfabeticamente() {
        Collections.sort(listaFiltrada, (p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()));
        adaptadorProductos.notifyDataSetChanged();
    }

    private void ordenarPrecioMenorMayor() {
        Collections.sort(listaFiltrada, Comparator.comparingDouble(Producto::getPrecio));
        adaptadorProductos.notifyDataSetChanged();
    }

    private void ordenarPrecioMayorMenor() {
        Collections.sort(listaFiltrada, (p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
        adaptadorProductos.notifyDataSetChanged();
    }


}