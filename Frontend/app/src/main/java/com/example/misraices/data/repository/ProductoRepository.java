package com.example.misraices.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.service.ProductoService;
import com.google.gson.Gson;

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

    public MutableLiveData<List<Producto>> ejecutarPeticion(Call<List<Producto>> call) {
        Log.e("Retrofit", "Ejecutando petición a: " + call.request().url());
        final MutableLiveData<List<Producto>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    Log.e("Retrofit Response", new Gson().toJson(response.body()));
                    mdl.setValue(response.body());
                    Log.e("response", response.toString());
                } else {
                    Log.e("Retrofit Response", "Respuesta vacía o error: " + response.code());
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
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
