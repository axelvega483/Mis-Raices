package com.example.misraices.data.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private final UsuarioService usuarioService;


    public UsuarioRepository() {
        this.usuarioService = ApiRetrofit.getRetrofitInstance().create(UsuarioService.class);
    }
    public <T> MutableLiveData<Result<T>> ejecutarPeticion(Call<ApiRespo<T>> call) {
        final MutableLiveData<Result<T>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<T>>() {
            @Override
            public void onResponse(Call<ApiRespo<T>> call, Response<ApiRespo<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiRespo<T> apiRespo = response.body();
                    if (apiRespo.isExito()) {
                        mdl.setValue(new Result<>(apiRespo.getData()));
                    } else {
                        mdl.setValue(new Result<>(apiRespo.getMensaje()));
                    }
                } else {
                    mdl.setValue(new Result<>("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<T>> call, Throwable t) {
                mdl.setValue(new Result<>(t.getMessage()));
            }
        });
        return mdl;
    }

    public MutableLiveData<List<Usuario>> ejecutarPeticionLista(Call<List<Usuario>> call) {
        final MutableLiveData<List<Usuario>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    mdl.setValue(response.body());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
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
    public MutableLiveData<Result<Usuario>> solicitarToken(Usuario usuario) {
        return ejecutarPeticion(usuarioService.solicitarToken(usuario));
    }
    public MutableLiveData<Result<Usuario>> restablecerPassword(Usuario usuario) {
        return ejecutarPeticion(usuarioService.restablecerPassword(usuario));
    }
    public MutableLiveData<Result<Usuario>> editarUsuario(Integer id,Usuario usuario) {
        return ejecutarPeticion(usuarioService.actualizarUsuario(id,usuario));
    }
    public MutableLiveData<Result<Usuario>> obtenerId(Integer id) {
        return ejecutarPeticion(usuarioService.obtenerUsuarioPorId(id));
    }
    public MutableLiveData<Result<Usuario>>actualizarDireccion(Integer id, Direccion direccion) {
        return ejecutarPeticion(usuarioService.actualizarDireccion(id,direccion));
    }
}
