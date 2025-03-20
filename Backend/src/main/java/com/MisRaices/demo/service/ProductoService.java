package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.interfaz.ProductoInterfaz;
import com.MisRaices.demo.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements ProductoInterfaz {

    @Autowired
    private ProductoRepository repo;

    @Override
    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Producto> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Producto> listar() {
        return repo.findAll();
    }
}
