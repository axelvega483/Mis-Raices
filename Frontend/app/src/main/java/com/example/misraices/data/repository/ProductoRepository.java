package com.example.misraices.data.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.service.ProductoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {
    private final ProductoService productoService;

    public ProductoRepository() {
        this.productoService = ApiRetrofit.getRetrofitInstance().create(ProductoService.class);
    }

    public MutableLiveData<List<Producto>> ejecutarPeticion(Call<ApiRespo<List<Producto>>> call) {
        final MutableLiveData<List<Producto>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<List<Producto>>>() {
            @Override
            public void onResponse(Call<ApiRespo<List<Producto>>> call, Response<ApiRespo<List<Producto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isExito()) {
                    mdl.setValue(response.body().getData());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<List<Producto>>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
            }
        });
        return mdl;
    }

    public MutableLiveData<List<Producto>> obtenerProductos() {
        return ejecutarPeticion(productoService.obtenerProductos());
    }
    public MutableLiveData<List<Producto>> obtenerProductosPorCategoria(int Id) {
        return ejecutarPeticion(productoService.obtenerProductosPorCategoria(Id));
    }
    public MutableLiveData<List<Producto>> obtenerProductosPorNombre(String nombre) {
        return ejecutarPeticion(productoService.obtenerProductosPorNombre(nombre));
    }
}
