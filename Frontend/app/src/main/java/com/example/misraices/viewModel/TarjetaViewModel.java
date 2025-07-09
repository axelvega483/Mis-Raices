package com.example.misraices.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.ApiRespo;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.data.repository.TarjetaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TarjetaViewModel extends ViewModel {
    private final TarjetaRepository repo = new TarjetaRepository();

    private final MutableLiveData<List<TarjetaCredito>> tarjetasLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<TarjetaCredito> tarjetaMutableLiveData = new MutableLiveData<>();

    public void cargarTarjetas() {
        repo.obtenerTarjetas().observeForever(tarjetas -> {
            if (tarjetas != null) {
                tarjetasLiveData.postValue(tarjetas);
            }
        });
    }

    public LiveData<List<TarjetaCredito>> obtenerTarjetas() {
        return tarjetasLiveData;
    }

    public MutableLiveData<ApiRespo<TarjetaCredito>> crearTarjeta(TarjetaCredito tarjeta) {
        List<TarjetaCredito> actuales = tarjetasLiveData.getValue();
        if (actuales == null) actuales = new ArrayList<>();
        actuales.add(tarjeta);
        tarjetasLiveData.setValue(actuales);

        MutableLiveData<ApiRespo<TarjetaCredito>> resultado = repo.crearTarjeta(tarjeta);
        resultado.observeForever(response -> {
            if (response != null && !response.isExito()) {
                List<TarjetaCredito> list = tarjetasLiveData.getValue();
                if (list != null) {
                    list.remove(tarjeta);
                    tarjetasLiveData.postValue(list);
                }
            }
        });

        return resultado;
    }

    public LiveData<TarjetaCredito> getTarjetaLiveData() {
        return tarjetaMutableLiveData;
    }

    public void setTarjetaLiveData(TarjetaCredito tarjeta) {
        tarjetaMutableLiveData.postValue(tarjeta);
    }

    public MutableLiveData<ApiRespo<TarjetaCredito>> editarTarjeta(Integer id, TarjetaCredito tarjeta) {
        return repo.editarTarjeta(id, tarjeta);
    }

    public MutableLiveData<Boolean> eliminarTarjeta(Integer id) {
        return repo.eliminarTarjeta(id);
    }
}