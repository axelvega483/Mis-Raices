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
import android.view.Menu;
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
import com.example.misraices.data.util.ExposicionProducto;
import com.example.misraices.data.util.OrigenProducto;
import com.example.misraices.data.util.TamañoProducto;
import com.example.misraices.databinding.FragmentHomeBinding;
import com.example.misraices.view.adapter.AdaptadorCategorias;
import com.example.misraices.view.adapter.AdaptadorProductos;
import com.example.misraices.viewModel.CategoriaViewModel;
import com.example.misraices.viewModel.ProductoViewModel;
import com.example.misraices.viewModel.UsuarioViewModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductoViewModel productoViewModel;
    private CategoriaViewModel categoriaViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Handler handler = new Handler();
    private Runnable searchRunnable;
    private int usuarioId;
    private AdaptadorProductos adaptadorProductos;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initViewModels();
        setupRecyclerView();
        observeViewModels();
        productoViewModel.cargarProductos();
        setupListeners();
        return binding.getRoot();
    }

    private void initViewModels() {
        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
        categoriaViewModel = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        SharedPreferences prefs = requireActivity().getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);
    }

    private void setupRecyclerView() {
        adaptadorProductos = new AdaptadorProductos(new ArrayList<>(), requireContext(), this::abrirDetalleProducto);
        binding.recyclerViewProducto.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewProducto.setAdapter(adaptadorProductos);
    }

    private void observeViewModels() {
        productoViewModel.getProductosLiveData().observe(getViewLifecycleOwner(), productos -> {
            adaptadorProductos.updateData(productos);
        });

        categoriaViewModel.obtenerCategorias().observe(getViewLifecycleOwner(), categorias -> {
            if (categorias != null) {
                binding.recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewCategoria.setAdapter(new AdaptadorCategorias(categorias, requireContext(), this::mostrarProductosPorCategoria));
            }
        });

        usuarioViewModel.obtenerId(usuarioId).observe(getViewLifecycleOwner(), result -> {
            String direccionTexto = "Dirección";
            if (result != null && result.getData() != null) {
                Direccion direccion = result.getData().getDireccion();
                if (direccion != null) {
                    String calle = direccion.getCalle() != null ? direccion.getCalle() : "";
                    Long numero = direccion.getNumero() != null ? direccion.getNumero() : 0L;
                    direccionTexto = (calle + " " + numero).trim();
                    if (direccionTexto.isEmpty()) direccionTexto = "Dirección";
                }
            }
            binding.mapDireccionUsuario.setText(direccionTexto);
        });
    }

    private void setupListeners() {
        binding.mapDireccionUsuario.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, new MapaFragment())
                    .addToBackStack(null)
                    .commit();
        });

        binding.verTodoTxt.setOnClickListener(v -> {
            productoViewModel.cargarProductos();
            productoViewModel.limpiarFiltros();
            binding.btnFiltro.setText("Filtrar");
            binding.btnFiltroOrden.setText("Ordenar");
        });

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productoViewModel.obtenerProductosPorNombre(query).observe(getViewLifecycleOwner(), resp -> {
                    if (resp != null && resp.isExito())
                        adaptadorProductos.updateData(resp.getData());
                    else
                        Toast.makeText(requireContext(), "No se encontraron productos para: " + query, Toast.LENGTH_SHORT).show();
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeCallbacks(searchRunnable);
                searchRunnable = () -> {
                    if (newText.isEmpty()) productoViewModel.cargarProductos();
                    else
                        productoViewModel.obtenerProductosPorNombre(newText).observe(getViewLifecycleOwner(), resp -> {
                            if (resp != null && resp.isExito())
                                adaptadorProductos.updateData(resp.getData());
                        });
                };
                handler.postDelayed(searchRunnable, 300);
                return true;
            }
        });

        binding.btnFiltroOrden.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), v);
            popup.getMenuInflater().inflate(R.menu.orden_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.orden_alfabetico) {
                    productoViewModel.ordenarAlfabeticamente();
                    binding.btnFiltroOrden.setText("A-Z");
                } else if (item.getItemId() == R.id.orden_precio_menor_mayor) {
                    productoViewModel.ordenarPrecioMenorMayor();
                    binding.btnFiltroOrden.setText("Precio ⬆");

                } else if (item.getItemId() == R.id.orden_precio_mayor_menor) {
                    productoViewModel.ordenarPrecioMayorMenor();
                    binding.btnFiltroOrden.setText("Precio ⬇");
                }

                return true;
            });
            popup.show();
        });

        binding.btnFiltro.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), v);
            popup.getMenuInflater().inflate(R.menu.filtro_menu, popup.getMenu());
            marcarFiltrosSeleccionados(popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.sol_pleno_menu) {
                    productoViewModel.toggleFiltroExposicion(ExposicionProducto.sol_pleno);
                } else if (item.getItemId() == R.id.luz_indirecta_menu) {
                    productoViewModel.toggleFiltroExposicion(ExposicionProducto.luz_indirecta);
                } else if (item.getItemId() == R.id.pequenio_menu) {
                    productoViewModel.toggleFiltroTamano(TamañoProducto.pequeno);
                } else if (item.getItemId() == R.id.mediano_menu) {
                    productoViewModel.toggleFiltroTamano(TamañoProducto.mediano);
                } else if (item.getItemId() == R.id.grande_menu) {
                    productoViewModel.toggleFiltroTamano(TamañoProducto.grande);
                } else if (item.getItemId() == R.id.nativa_menu) {
                    productoViewModel.toggleFiltroOrigen(OrigenProducto.nativa);
                } else if (item.getItemId() == R.id.exotica_menu) {
                    productoViewModel.toggleFiltroOrigen(OrigenProducto.exotica);
                }
                actualizarTextoFiltro();
                return true;
            });

            popup.show();
        });
    }

    private void marcarFiltrosSeleccionados(Menu menu) {
        if (productoViewModel.getExposicionesSeleccionadas().contains(ExposicionProducto.sol_pleno))
            menu.findItem(R.id.sol_pleno_menu).setChecked(true);
        if (productoViewModel.getExposicionesSeleccionadas().contains(ExposicionProducto.luz_indirecta))
            menu.findItem(R.id.luz_indirecta_menu).setChecked(true);
        if (productoViewModel.getTamaniosSeleccionados().contains(TamañoProducto.pequeno))
            menu.findItem(R.id.pequenio_menu).setChecked(true);
        if (productoViewModel.getTamaniosSeleccionados().contains(TamañoProducto.mediano))
            menu.findItem(R.id.mediano_menu).setChecked(true);
        if (productoViewModel.getTamaniosSeleccionados().contains(TamañoProducto.grande))
            menu.findItem(R.id.grande_menu).setChecked(true);
        if (productoViewModel.getOrigenesSeleccionados().contains(OrigenProducto.nativa))
            menu.findItem(R.id.nativa_menu).setChecked(true);
        if (productoViewModel.getOrigenesSeleccionados().contains(OrigenProducto.exotica))
            menu.findItem(R.id.exotica_menu).setChecked(true);
    }

    private void actualizarTextoFiltro() {
        int totalFiltros = productoViewModel.cantidadTotalDeFiltros();
        binding.btnFiltro.setText(totalFiltros > 0 ? "Filtrando (" + totalFiltros + ")" : "Filtrar");
    }

    private void mostrarProductosPorCategoria(Categoria categoria) {
        productoViewModel.obtenerProductosPorCategoria(categoria.getId()).observe(getViewLifecycleOwner(), resp -> {
            if (resp != null && resp.isExito())
                adaptadorProductos.updateData(resp.getData());
        });
    }

    private void abrirDetalleProducto(Producto producto) {
        ProductoDetalleFragment fragment = new ProductoDetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("producto", producto);
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}