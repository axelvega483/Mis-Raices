package com.MisRaices.ProyectoFinal.service;

import com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO.CategoriaGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO.CategoriaMapper;
import com.MisRaices.ProyectoFinal.entity.Categoria;
import com.MisRaices.ProyectoFinal.interfaz.CategoriaInterfaz;
import com.MisRaices.ProyectoFinal.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements CategoriaInterfaz {

    @Autowired
    private CategoriaRepository repo;
    @Autowired
    private CategoriaMapper mapper;

    @Override
    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<CategoriaGetDTO> obtener(Integer id) {
        return repo.findById(id).map(mapper::toDTO);
    }

    @Override
    public List<CategoriaGetDTO> listar() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public Optional<Categoria> obtenerNombre(String nombre) {
        return repo.findByNombre(nombre);
    }
    @Override
    public boolean existeCategoria(Integer id) {
        return repo.existsById(id);
    }
}
