package com.example.misraices.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.repository.UsuarioRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioViewModel extends AndroidViewModel {
    private UsuarioRepository usuarioRepository;
    private MutableLiveData<Usuario> usuarioLiveData;
    private MutableLiveData<List<Pedido>> pedidosLiveData;
    private MutableLiveData<String> errorLiveData;

    public UsuarioViewModel(Application application) {
        super(application);
        this.usuarioRepository = new UsuarioRepository(application.getApplicationContext());
        this.usuarioLiveData = new MutableLiveData<>();
        this.pedidosLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }

    public MutableLiveData<List<Pedido>> getPedidosLiveData() {
        return pedidosLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.getUsuarioService().crearUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuarioLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error al crear el usuario");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }

        });
    }

    public void activarCuenta(Usuario usuario) {
        usuarioRepository.getUsuarioService().activarCuenta(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuarioLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error al activar la cuenta");
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void login(Usuario usuario) {
        usuarioRepository.getUsuarioService().login(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuarioLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error al iniciar sesión");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

}
