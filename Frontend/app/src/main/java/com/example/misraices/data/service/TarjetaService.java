package com.example.misraices.data.service;

import com.example.misraices.data.model.TarjetaCredito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TarjetaService {
    @GET("/tarjeta")
    Call<List<TarjetaCredito>> obtenerTarjetas();

    @POST("/tarjeta")
    Call<TarjetaCredito> crearTarjeta(@Body TarjetaCredito tarjeta);

    @PUT("/tarjeta/{id}")
    Call<TarjetaCredito> actualizarTarjeta(@Path("id") Integer id, @Body TarjetaCredito tarjeta);

    @DELETE("/tarjeta/{id}")
    Call<Void> eliminarTarjeta(@Path("id") Integer id);
}
