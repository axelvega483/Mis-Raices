package com.example.misraices.data.service;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {

    @POST("/pedido")
    Call<ApiRespo<Pedido>> crear(@Body Pedido pedido);

    @GET("/pedido/{id}")
    Call<ApiRespo<Pedido>> obtenerPorId(@Path("id") int id);

    @GET("/pedido")
    Call<ApiRespo<List<Pedido>>> obtener();

    @POST("/pedido/finalizarCompra/{pedidoId}/{tarjetaId}")
    Call<ApiRespo<Pedido>> finalizarCompra(@Path("pedidoId") int pedidoId, @Path("tarjetaId") int tarjetaId);
}
