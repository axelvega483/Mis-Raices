package com.example.misraices.data.service;

import com.example.misraices.data.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {

    @POST("/pedido")
    Call<Pedido>crear(@Body Pedido pedido);

    @GET("/pedido")
    Call<List<Pedido>>obtener();

    @POST("/pedido/finalizarCompra/{pedidoId}/{tarjetaId}")
    Call<Pedido>finalizarCompra(@Path("pedidoId") int pedidoId, @Path("tarjetaId") int tarjetaId);
}
