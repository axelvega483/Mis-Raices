package com.example.misraices.data.SQLite.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.misraices.data.SQLite.Data.AppDatabase;
import com.example.misraices.data.SQLite.Model.Planta;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Producto;
import com.example.misraices.data.util.EstadoPedido;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MisPlantasViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;


    public MisPlantasViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstancia(application);
    }

    public LiveData<List<Planta>> cargarPlantasPorUsuarioLiveData(int usuarioId) {
        return appDatabase.plantaDao().obtenerTodas(usuarioId);
    }

    public void sincronizarPlantasDesdePedidos(List<Pedido> pedidos) {
        if (pedidos == null) return;

        Executors.newSingleThreadExecutor().execute(() -> {
            for (Pedido pedido : pedidos) {
                if (pedido.getEstado().equals(EstadoPedido.CANCELADO)) {
                    appDatabase.plantaDao().eliminarPlantasPorPedidoId(pedido.getId());
                }
            }

            List<Planta> plantasParaGuardar = new ArrayList<>();
            for (Pedido pedido : pedidos) {
                if (!pedido.getEstado().equals(EstadoPedido.CANCELADO) && pedido.getDetalle() != null) {
                    for (PedidoDetalle detalle : pedido.getDetalle()) {
                        Producto producto = detalle.getProducto();
                        Planta planta = new Planta();
                        planta.setPlantaIdServidor(producto.getId());
                        planta.setNombre(producto.getNombre());
                        planta.setImg(producto.getImg());
                        planta.setCuidados(producto.getCuidado());
                        planta.setVideo(producto.getVideo());
                        planta.setPedidoId(pedido.getId());
                        planta.setUsuarioId(pedido.getUsuario().getId());
                        plantasParaGuardar.add(planta);
                    }
                }
            }

            appDatabase.plantaDao().insertarPlanta(plantasParaGuardar);
        });
    }
}