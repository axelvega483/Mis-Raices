package com.example.misraices.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.misraices.data.model.Categoria;
import com.example.misraices.data.repository.CategoriaRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriaViewModel extends ViewModel {
    private final CategoriaRepository repo = new CategoriaRepository();
    private final MutableLiveData<List<Categoria>> categoriaMutableLiveData = new MutableLiveData<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public void setCategoriaMutableLiveData(List<Categoria> categorias) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                categoriaMutableLiveData.postValue(categorias);
            }
        });
    }
    public LiveData<List<Categoria>> getCategoriaLiveData() {
        return categoriaMutableLiveData;
    }

    public MutableLiveData<List<Categoria>> obtenerCategorias() {
        return repo.obtenerCategorias();
    }

}
