package com.example.misraices.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.repository.ProductoRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductoViewModel extends ViewModel {
    private final ProductoRepository repo = new ProductoRepository();
    private MutableLiveData<List<Producto>> productoMutableLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public void setProductoMutableLiveData(List<Producto> productos) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productoMutableLiveData.postValue(productos);
            }
        });
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

}
