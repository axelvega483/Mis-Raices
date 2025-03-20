package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Imagen;
import com.MisRaices.demo.interfaz.ImagenInterfaz;
import com.MisRaices.demo.repository.ImagenRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenService implements ImagenInterfaz {

    @Autowired
    private ImagenRepository repo;

    @Override
    public Imagen guardar(Imagen imagen) {
        return repo.save(imagen);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Imagen> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Imagen> listar() {
        return repo.findAll();
    }
}
