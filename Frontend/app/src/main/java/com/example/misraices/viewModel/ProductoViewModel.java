package com.example.misraices.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.repository.ProductoRepository;
import com.example.misraices.data.util.ExposicionProducto;
import com.example.misraices.data.util.OrigenProducto;
import com.example.misraices.data.util.TamañoProducto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductoViewModel extends ViewModel {
    private final ProductoRepository repo = new ProductoRepository();
    private MediatorLiveData<List<Producto>> productosLiveData = new MediatorLiveData<>();
    private LiveData<ApiRespo<List<Producto>>> source;

    private List<Producto> listaOriginal = new ArrayList<>();
    private List<Producto> listaFiltrada = new ArrayList<>();

    private final Set<ExposicionProducto> exposicionesSeleccionadas = new HashSet<>();
    private final Set<TamañoProducto> tamaniosSeleccionados = new HashSet<>();
    private final Set<OrigenProducto> origenesSeleccionados = new HashSet<>();

    public LiveData<List<Producto>> getProductosLiveData() {
        return productosLiveData;
    }
    public void limpiarFiltros(){
        exposicionesSeleccionadas.clear();
        tamaniosSeleccionados.clear();
        origenesSeleccionados.clear();

    }
    public void cargarProductos() {
        if (source != null) {
            productosLiveData.removeSource(source);
        }
        source = repo.obtenerProductos();
        productosLiveData.addSource(source, apiResponse -> {
            if (apiResponse != null && apiResponse.isExito() && apiResponse.getData() != null) {
                listaOriginal.clear();
                listaOriginal.addAll(apiResponse.getData());
                aplicarFiltrosYOrden();
            } else {
                listaOriginal.clear();
                listaFiltrada.clear();
                productosLiveData.setValue(Collections.emptyList());
            }
        });
    }
    public Set<ExposicionProducto> getExposicionesSeleccionadas() {
        return Collections.unmodifiableSet(exposicionesSeleccionadas);
    }

    public Set<TamañoProducto> getTamaniosSeleccionados() {
        return Collections.unmodifiableSet(tamaniosSeleccionados);
    }

    public Set<OrigenProducto> getOrigenesSeleccionados() {
        return Collections.unmodifiableSet(origenesSeleccionados);
    }

    public int cantidadTotalDeFiltros() {
        return exposicionesSeleccionadas.size() + tamaniosSeleccionados.size() + origenesSeleccionados.size();
    }
    public void aplicarFiltrosYOrden() {
        listaFiltrada = listaOriginal.stream()
                .filter(p -> exposicionesSeleccionadas.isEmpty() || exposicionesSeleccionadas.contains(p.getExposicion()))
                .filter(p -> tamaniosSeleccionados.isEmpty() || tamaniosSeleccionados.contains(p.getTamano()))
                .filter(p -> origenesSeleccionados.isEmpty() || origenesSeleccionados.contains(p.getOrigen()))
                .filter(p -> p.getStock() > 0)
                .collect(Collectors.toList());
        productosLiveData.setValue(listaFiltrada);
    }

    public void toggleFiltroExposicion(ExposicionProducto filtro) {
        if (exposicionesSeleccionadas.contains(filtro)) exposicionesSeleccionadas.remove(filtro);
        else exposicionesSeleccionadas.add(filtro);
        aplicarFiltrosYOrden();
    }

    public void toggleFiltroTamano(TamañoProducto filtro) {
        if (tamaniosSeleccionados.contains(filtro)) tamaniosSeleccionados.remove(filtro);
        else tamaniosSeleccionados.add(filtro);
        aplicarFiltrosYOrden();
    }

    public void toggleFiltroOrigen(OrigenProducto filtro) {
        if (origenesSeleccionados.contains(filtro)) origenesSeleccionados.remove(filtro);
        else origenesSeleccionados.add(filtro);
        aplicarFiltrosYOrden();
    }

    public void ordenarAlfabeticamente() {
        listaFiltrada.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
        productosLiveData.setValue(listaFiltrada);
    }

    public void ordenarPrecioMenorMayor() {
        listaFiltrada.sort(Comparator.comparingDouble(Producto::getPrecio));
        productosLiveData.setValue(listaFiltrada);
    }

    public void ordenarPrecioMayorMenor() {
        listaFiltrada.sort((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
        productosLiveData.setValue(listaFiltrada);
    }

    public LiveData<ApiRespo<List<Producto>>> obtenerProductos() {
        return repo.obtenerProductos();
    }

    public LiveData<ApiRespo<List<Producto>>> obtenerProductosPorCategoria(int Id) {
        return repo.obtenerProductosPorCategoria(Id);
    }

    public LiveData<ApiRespo<List<Producto>>> obtenerProductosPorNombre(String nombre) {
        return repo.obtenerProductosPorNombre(nombre);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (source != null) {
            productosLiveData.removeSource(source);
        }
    }
}