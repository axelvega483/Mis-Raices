package com.example.misraices.data.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Categoria;
import com.example.misraices.data.service.CategoriaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaRepository {
    private final CategoriaService categoriaService;

    public CategoriaRepository (){
        this.categoriaService= ApiRetrofit.getRetrofitInstance().create(CategoriaService.class);
    }

    public MutableLiveData<List<Categoria>> ejecutarPeticion(Call<ApiRespo<List<Categoria>>> call) {
        final MutableLiveData<List<Categoria>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<List<Categoria>>>() {
            @Override
            public void onResponse(Call<ApiRespo<List<Categoria>>> call, Response<ApiRespo<List<Categoria>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isExito()) {
                    mdl.setValue(response.body().getData());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<List<Categoria>>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
            }
        });
        return mdl;
    }

    public MutableLiveData<List<Categoria>> obtenerCategorias() {
        return ejecutarPeticion(categoriaService.obtenerCategorias());
    }
}
