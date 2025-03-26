package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.interfaz.CategoriaInterfaz;
import com.MisRaices.demo.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements CategoriaInterfaz {

    @Autowired
    private CategoriaRepository repo;

    @Override
    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Categoria> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Categoria> listar() {
        return repo.findAll();
    }
    
    public Optional<Categoria>obtenerNombre(String nombre){
        return  repo.findByNombre(nombre);
    }

}
