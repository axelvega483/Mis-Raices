package com.example.misraices.data.SQLite.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.SQLite.Data.AppDatabase;
import com.example.misraices.data.SQLite.Model.Planta;
import com.example.misraices.data.model.Pedido;
import com.example.misraices.data.model.PedidoDetalle;
import com.example.misraices.data.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MisPlantasViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private MutableLiveData<List<Planta>> plantasLiveData = new MutableLiveData<>();

    public MisPlantasViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstancia(application);
        cargarPlantasDesdeBD();
    }

    public LiveData<List<Planta>> getPlantas() {
        return plantasLiveData;
    }

    private void cargarPlantasDesdeBD() {
        appDatabase.plantaDao().obtenerTodas().observeForever(plantas -> {
            plantasLiveData.postValue(plantas);
        });
    }

    public void sincronizarPlantasDesdePedidos(List<Pedido> pedidos) {
        if (pedidos == null) {
            return;
        } else {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Planta> plantasParaGuardar = new ArrayList<>();

                for (Pedido pedido : pedidos) {
                    if (pedido.getDetalle() != null) {
                        for (PedidoDetalle detalle : pedido.getDetalle()) {
                            Producto producto = detalle.getProducto();
                            Planta planta = new Planta();
                            planta.setPlantaIdServidor(producto.getId());
                            planta.setNombre(producto.getNombre());
                            planta.setImg(producto.getImg());
                            planta.setCuidados(producto.getCuidado());
                            planta.setVideo(producto.getVideo());
                            Log.e("MisPlantasViewModel", "Planta: " + planta.toString());
                            plantasParaGuardar.add(planta);
                            Log.e("MisPlantasViewModel", "Plantas para guardar: " + planta.toString());
                        }
                    }
                    Log.e("MisPlantasFragment", "Cantidad de pedidos: " + pedidos.size());

                }

                appDatabase.plantaDao().insertarPlanta(plantasParaGuardar);
            });
        }
    }

}
