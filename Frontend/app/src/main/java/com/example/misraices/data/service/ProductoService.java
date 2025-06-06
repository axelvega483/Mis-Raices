package com.example.misraices.data.service;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoService {

    @GET("/producto")
    Call<ApiRespo<List<Producto>>> obtenerProductos();

    @GET("/producto/categoria/{Id}")
    Call<ApiRespo<List<Producto>>> obtenerProductosPorCategoria(@Path("Id") int Id);

    @GET("/producto/nombre/{nombre}")
    Call<ApiRespo<List<Producto>>> obtenerProductosPorNombre(@Path("nombre") String nombre);
}
