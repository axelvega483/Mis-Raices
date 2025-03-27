package com.example.misraices.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.service.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private final UsuarioService usuarioService;


    public UsuarioRepository() {
        this.usuarioService = ApiRetrofit.getRetrofitInstance().create(UsuarioService.class);
    }

    public <T> MutableLiveData<Result<T>> ejecutarPeticion(Call<T> call) {
        final MutableLiveData<Result<T>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.e("response", response.toString());
                if (response.isSuccessful()) {
                    mdl.setValue(new Result<>(response.body()));
                } else {
                    mdl.setValue(new Result<>("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                mdl.setValue(new Result<>(t.getMessage()));
            }
        });
        return mdl;
    }


    public MutableLiveData<Result<Usuario>> crearUsuario(Usuario usuario) {
        return ejecutarPeticion(usuarioService.crearUsuario(usuario));
    }

    public MutableLiveData<Result<Usuario>> activarCuenta(Usuario usuario) {
        return ejecutarPeticion(usuarioService.activarCuenta(usuario));
    }

    public MutableLiveData<Result<Usuario>> login(Usuario usuario) {
        return ejecutarPeticion(usuarioService.login(usuario));
    }

}
