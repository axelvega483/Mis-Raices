package com.example.misraices.data.service;

import com.example.misraices.data.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoService {

    @GET("/producto")
    Call<List<Producto>> obtenerProductos();

    @GET("/producto/categoria/{Id}")
    Call<List<Producto>> obtenerProductosPorCategoria(@Path("Id") int Id);

    @GET("/producto/nombre/{nombre}")
    Call<List<Producto>> obtenerProductosPorNombre(@Path("nombre") String nombre);
}
