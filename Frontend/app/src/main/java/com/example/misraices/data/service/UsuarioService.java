package com.example.misraices.data.service;

import com.example.misraices.data.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {
    @POST("/autenticacion/registro")
    Call<Usuario> crearUsuario(@Body Usuario usuario);

    @POST("/autenticacion/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("/autenticacion/activarCuenta")
    Call<Usuario> activarCuenta(@Body Usuario usuario);

    @POST("/autenticacion/solicitarToken")
    Call<Usuario> solicitarToken(@Body Usuario usuario);

    @POST("/autenticacion/restablecerPassword")
    Call<Usuario>restablecerPassword(@Body Usuario usuario);

    @GET("/usuario")
    Call<Usuario> obtenerUsuario();

    @GET("/usuario/{id}")
    Call<Usuario> obtenerUsuarioPorId(@Path("id") Integer id);

    @PUT("/usuario/{id}")
    Call<Usuario> actualizarUsuario(@Path("id") Integer id, @Body Usuario usuario);

    @DELETE("/usuario/{id}")
    Call<Void> eliminarUsuario(@Path("id") Integer id);
}
