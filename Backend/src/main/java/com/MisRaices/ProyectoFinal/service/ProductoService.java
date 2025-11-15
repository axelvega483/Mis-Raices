package com.MisRaices.ProyectoFinal.service;

import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoMapper;
import com.MisRaices.ProyectoFinal.entity.Producto;
import com.MisRaices.ProyectoFinal.interfaz.ProductoInterfaz;
import com.MisRaices.ProyectoFinal.repository.CategoriaRepository;
import com.MisRaices.ProyectoFinal.repository.ProductoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements ProductoInterfaz {

    @Autowired
    private ProductoRepository repo;
    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private ProductoMapper mapper;

    @Override
    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }


    @Override
    public Optional<ProductoGetDTO> obtener(Integer id) {
        return repo.findById(id).map(producto -> mapper.toDTO(producto));
    }

    @Override
    public List<ProductoGetDTO> listar() {
        return mapper.dtoList(repo.findAll());
    }

    @Override
    public List<ProductoGetDTO> listarPorCategoria(Integer categoriaId) {
        if (!categoriaRepo.existsById(categoriaId)) {
            return Collections.emptyList();
        }
        return mapper.dtoList(repo.findByCategoriaId(categoriaId));
    }

    @Override
    public List<ProductoGetDTO> obtenerNombre(String nombre) {
        return mapper.dtoList(repo.findByNombre(nombre));
    }
}
