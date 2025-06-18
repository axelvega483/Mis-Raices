package com.example.misraices.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Direccion;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.repository.UsuarioRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UsuarioViewModel extends ViewModel {
    private final UsuarioRepository repo = new UsuarioRepository();
    private MutableLiveData<Usuario> usuarioMutableLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Boolean> direccionActualizada = new MutableLiveData<>(false);

    public LiveData<Boolean> getDireccionActualizada() {
        return direccionActualizada;
    }

    public void setDireccionActualizada(boolean valor) {
        direccionActualizada.setValue(valor);
    }

    public void setUsuarioLiveData(Usuario usuario) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioMutableLiveData.postValue(usuario);
            }
        });
    }

    public void verificarBackend(Consumer<Boolean> callback) {
        repo.verificarBackend(callback);
    }

    public LiveData<Usuario> getUsuarioLiveData() {
        return usuarioMutableLiveData;
    }

    public MutableLiveData<ApiRespo<Usuario>> crearUsuario(Usuario usuario) {
        return repo.crearUsuario(usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> activarCuenta(Usuario usuario) {
        return repo.activarCuenta(usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> login(Usuario usuario) {
        Log.e("MutableLiveDatausuario", usuario.toString());
        return repo.login(usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> solicitarToken(Usuario usuario) {
        Log.e("MutableLiveDatausuarioToken", usuario.toString());
        return repo.solicitarToken(usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> restablecerPassword(Usuario usuario) {
        Log.e("MutableLiveDatausuarioPassword", usuario.toString());
        return repo.restablecerPassword(usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> editarUsuario(Integer id, Usuario usuario) {
        return repo.editarUsuario(id, usuario);
    }

    public MutableLiveData<ApiRespo<Usuario>> obtenerId(Integer id) {
        return repo.obtenerId(id);
    }

    public MutableLiveData<ApiRespo<Usuario>> actualizarDireccion(Integer id, Direccion direccion) {
        return repo.actualizarDireccion(id, direccion);
    }
}
