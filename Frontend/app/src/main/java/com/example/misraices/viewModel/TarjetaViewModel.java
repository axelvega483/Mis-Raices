package com.example.misraices.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.Result;
import com.example.misraices.data.model.TarjetaCredito;
import com.example.misraices.data.repository.TarjetaRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TarjetaViewModel extends ViewModel {
    private final TarjetaRepository repo = new TarjetaRepository();
    private MutableLiveData<TarjetaCredito> tarjetaMutableLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void setTarjetaLiveData(TarjetaCredito tarjeta) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tarjetaMutableLiveData.postValue(tarjeta);
            }
        });
    }

    public MutableLiveData<TarjetaCredito> getTarjetaLiveData() {
        return tarjetaMutableLiveData;
    }
    public MutableLiveData<List<TarjetaCredito>> obtenerTarjetas() {
        return repo.obtenerTarjetas();
    }

    public MutableLiveData<Result<TarjetaCredito>> crearTarjeta(TarjetaCredito tarjeta) {
        return repo.crearTarjeta(tarjeta);
    }

    public MutableLiveData<Result<TarjetaCredito>> editarTarjeta(Integer id, TarjetaCredito tarjeta) {
        return repo.editarTarjeta(id, tarjeta);
    }

    public MutableLiveData<Result<Void>> eliminarTarjeta(Integer id) {
        return repo.eliminarTarjeta(id);
    }
}
