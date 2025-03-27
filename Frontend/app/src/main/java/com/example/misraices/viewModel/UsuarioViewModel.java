package com.example.misraices.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.Result;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.repository.UsuarioRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioViewModel extends ViewModel {
    private final UsuarioRepository repo = new UsuarioRepository();
    private MutableLiveData<Usuario> usuarioMutableLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public void setUsuarioLiveData(Usuario usuario) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioMutableLiveData.postValue(usuario);
            }
        });
    }


    public LiveData<Usuario> getUsuarioLiveData() {
        return usuarioMutableLiveData;
    }


    public MutableLiveData<Result<Usuario>> crearUsuario(Usuario usuario) {
        return repo.crearUsuario(usuario);
    }

    public MutableLiveData<Result<Usuario>> activarCuenta(Usuario usuario) {
        return repo.activarCuenta(usuario);
    }

    public MutableLiveData<Result<Usuario>> login(Usuario usuario) {
        Log.e("MutableLiveDatausuario", usuario.toString());
        return repo.login(usuario);
    }
}
