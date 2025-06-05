package com.example.misraices.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.Result;
import com.example.misraices.data.service.PedidoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepository {
    private final PedidoService pedidoService;

    public PedidoRepository() {
        this.pedidoService = ApiRetrofit.getRetrofitInstance().create(PedidoService.class);
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

    public MutableLiveData<List<Pedido>> ejecutarPeticionLista(Call<List<Pedido>> call) {
        final MutableLiveData<List<Pedido>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                Log.e("response", response.toString());
                if (response.isSuccessful()) {
                    mdl.setValue(response.body());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
            }
        });
        return mdl;
    }

    public MutableLiveData<Result<Pedido>> crear(Pedido pedido) {
        return ejecutarPeticion(pedidoService.crear(pedido));
    }

    public MutableLiveData<Result<Pedido>> obtenerPorId(int id) {
        return ejecutarPeticion(pedidoService.obtenerPorId(id));
    }

    public MutableLiveData<List<Pedido>> obtener() {
        return ejecutarPeticionLista(pedidoService.obtener());
    }

    public MutableLiveData<Result<Pedido>> finalizarCompra(int pedidoId, int tarjetaId) {
        return ejecutarPeticion(pedidoService.finalizarCompra(pedidoId, tarjetaId));
    }
}
