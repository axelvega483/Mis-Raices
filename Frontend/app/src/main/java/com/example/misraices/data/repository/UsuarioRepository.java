package com.example.misraices.data.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.service.UsuarioService;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private final UsuarioService usuarioService;

    public UsuarioRepository() {
        this.usuarioService = ApiRetrofit.getRetrofitInstance().create(UsuarioService.class);
    }
    public <T> MutableLiveData<ApiRespo<T>> ejecutarPeticion(Call<ApiRespo<T>> call) {
        final MutableLiveData<ApiRespo<T>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<T>>() {
            @Override
            public void onResponse(Call<ApiRespo<T>> call, Response<ApiRespo<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiRespo<T> apiRespo = response.body();
                    if (apiRespo.isExito()) {
                        mdl.setValue(new ApiRespo<>(apiRespo.getData()));
                    } else {
                        mdl.setValue(new ApiRespo<>(apiRespo.getMensaje()));
                    }
                } else {
                    mdl.setValue(new ApiRespo<>("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<T>> call, Throwable t) {
                mdl.setValue(new ApiRespo<>(t.getMessage()));
            }
        });
        return mdl;
    }


    public void verificarBackend(Consumer<Boolean> callback) {
        Call<ApiRespo<List<Usuario>>> call = usuarioService.obtenerUsuarios();
        call.enqueue(new Callback<ApiRespo<List<Usuario>>>() {
            @Override
            public void onResponse(Call<ApiRespo<List<Usuario>>> call, Response<ApiRespo<List<Usuario>>> response) {
                if (response.body() != null) {
                    Log.d("verificarBackend", "Mensaje: " + response.body().getMensaje());
                    Log.d("verificarBackend", "Exito: " + response.body().isExito());
                    Log.d("verificarBackend", "Data size: " + (response.body().getData() != null ? response.body().getData().size() : "null"));
                }
                boolean exito = response.isSuccessful() && response.body() != null && response.body().isExito();
                callback.accept(exito);
                callback.accept(exito);
            }

            @Override
            public void onFailure(Call<ApiRespo<List<Usuario>>> call, Throwable t) {
                callback.accept(false);
            }
        });
    }


    public MutableLiveData<ApiRespo<Usuario>> crearUsuario(Usuario usuario) {
        return ejecutarPeticion(usuarioService.crearUsuario(usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> activarCuenta(Usuario usuario) {
        return ejecutarPeticion(usuarioService.activarCuenta(usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> login(Usuario usuario) {
        return ejecutarPeticion(usuarioService.login(usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> solicitarToken(Usuario usuario) {
        return ejecutarPeticion(usuarioService.solicitarToken(usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> restablecerPassword(Usuario usuario) {
        return ejecutarPeticion(usuarioService.restablecerPassword(usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> editarUsuario(Integer id, Usuario usuario) {
        return ejecutarPeticion(usuarioService.actualizarUsuario(id, usuario));
    }

    public MutableLiveData<ApiRespo<Usuario>> obtenerId(Integer id) {
        return ejecutarPeticion(usuarioService.obtenerUsuarioPorId(id));
    }

    public MutableLiveData<ApiRespo<Usuario>> actualizarDireccion(Integer id, Direccion direccion) {
        return ejecutarPeticion(usuarioService.actualizarDireccion(id, direccion));
    }

}
