package com.example.misraices.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.Categoria;
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
        this.tarjetaService = ApiRetrofit.getRetrofitInstance().create(TarjetaService.class);
    }

    public MutableLiveData<List<TarjetaCredito>> ejecutarPeticionLista(Call<ApiRespo<List<TarjetaCredito>>> call) {
        final MutableLiveData<List<TarjetaCredito>> liveData = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<List<TarjetaCredito>>>() {
            @Override
            public void onResponse(Call<ApiRespo<List<TarjetaCredito>>> call, Response<ApiRespo<List<TarjetaCredito>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isExito()) {
                    liveData.setValue(response.body().getData());
                } else {
                    liveData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<List<TarjetaCredito>>> call, Throwable t) {
                liveData.setValue(new ArrayList<>());
            }
        });
        return liveData;
    }

    public MutableLiveData<ApiRespo<TarjetaCredito>> ejecutarPeticion(Call<ApiRespo<TarjetaCredito>> call) {
        final MutableLiveData<ApiRespo<TarjetaCredito>> liveData = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<TarjetaCredito>>() {
            @Override
            public void onResponse(Call<ApiRespo<TarjetaCredito>> call, Response<ApiRespo<TarjetaCredito>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<TarjetaCredito>> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<List<TarjetaCredito>> obtenerTarjetas() {
        return ejecutarPeticionLista(tarjetaService.obtenerTarjetas());
    }

    public MutableLiveData<ApiRespo<TarjetaCredito>> crearTarjeta(TarjetaCredito tarjeta) {
        return ejecutarPeticion(tarjetaService.crearTarjeta(tarjeta));
    }

    public MutableLiveData<ApiRespo<TarjetaCredito>> editarTarjeta(Integer id, TarjetaCredito tarjeta) {
        return ejecutarPeticion(tarjetaService.actualizarTarjeta(id, tarjeta));
    }

    public MutableLiveData<Boolean> eliminarTarjeta(Integer id) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        tarjetaService.eliminarTarjeta(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                liveData.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                liveData.setValue(false);
            }
        });
        return liveData;
    }

}
