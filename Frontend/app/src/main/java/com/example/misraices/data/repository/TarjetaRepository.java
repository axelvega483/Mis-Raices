package com.example.misraices.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.data.model.Usuario;
import com.example.misraices.data.service.TarjetaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarjetaRepository {
    private TarjetaService tarjetaService;

    public TarjetaRepository() {
        this.tarjetaService =  ApiRetrofit.getRetrofitInstance().create(TarjetaService.class);
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
    public MutableLiveData<List<TarjetaCredito>> ejecutarPeticionLista(Call<List<TarjetaCredito>> call) {
        final MutableLiveData<List<TarjetaCredito>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<List<TarjetaCredito>>() {
            @Override
            public void onResponse(Call<List<TarjetaCredito>> call, Response<List<TarjetaCredito>> response) {
                if (response.isSuccessful()) {
                    mdl.setValue(response.body());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<TarjetaCredito>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
            }
        });
        return mdl;
    }
    public MutableLiveData<List<TarjetaCredito>> obtenerTarjetas() {
        return ejecutarPeticionLista(tarjetaService.obtenerTarjetas());
    }
    public MutableLiveData<Result<TarjetaCredito>> crearTarjeta(TarjetaCredito tarjeta) {
        return ejecutarPeticion(tarjetaService.crearTarjeta(tarjeta));
    }
    public MutableLiveData<Result<TarjetaCredito>> editarTarjeta(Integer id,TarjetaCredito tarjeta) {
        return ejecutarPeticion(tarjetaService.actualizarTarjeta(id,tarjeta));
    }
    public MutableLiveData<Result<Void>> eliminarTarjeta(Integer id) {
        return ejecutarPeticion(tarjetaService.eliminarTarjeta(id));
    }
}
