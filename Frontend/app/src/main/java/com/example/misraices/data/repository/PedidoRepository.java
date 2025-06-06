package com.example.misraices.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.api.ApiRetrofit;
import com.example.misraices.data.model.ApiRespo;
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

    public MutableLiveData<Result<ApiRespo<Pedido>>> ejecutarPeticion(Call<ApiRespo<Pedido>> call) {
        final MutableLiveData<Result<ApiRespo<Pedido>>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<Pedido>>() {
            @Override
            public void onResponse(Call<ApiRespo<Pedido>> call, Response<ApiRespo<Pedido>> response) {
                Log.e("response", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    mdl.setValue(new Result<>(response.body()));
                } else {
                    mdl.setValue(new Result<>("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<Pedido>> call, Throwable t) {
                mdl.setValue(new Result<>(t.getMessage()));
            }
        });
        return mdl;
    }

    public MutableLiveData<List<Pedido>> ejecutarPeticionLista(Call<ApiRespo<List<Pedido>>> call) {
        final MutableLiveData<List<Pedido>> mdl = new MutableLiveData<>();
        call.enqueue(new Callback<ApiRespo<List<Pedido>>>() {
            @Override
            public void onResponse(Call<ApiRespo<List<Pedido>>> call, Response<ApiRespo<List<Pedido>>> response) {
                Log.e("response", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    mdl.setValue(response.body().getData());
                } else {
                    mdl.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiRespo<List<Pedido>>> call, Throwable t) {
                mdl.setValue(new ArrayList<>());
            }
        });
        return mdl;
    }

    public MutableLiveData<Result<ApiRespo<Pedido>>> crear(Pedido pedido) {
        return ejecutarPeticion(pedidoService.crear(pedido));
    }

    public MutableLiveData<Result<ApiRespo<Pedido>>> obtenerPorId(int id) {
        return ejecutarPeticion(pedidoService.obtenerPorId(id));
    }

    public MutableLiveData<List<Pedido>> obtener() {
        return ejecutarPeticionLista(pedidoService.obtener());
    }

    public MutableLiveData<Result<ApiRespo<Pedido>>> finalizarCompra(int pedidoId, int tarjetaId) {
        return ejecutarPeticion(pedidoService.finalizarCompra(pedidoId, tarjetaId));
    }
}