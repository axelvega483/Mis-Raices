package com.example.misraices.data.service;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaService {
    @GET("/categoria")
    Call<ApiRespo<List<Categoria>>> obtenerCategorias();
}
