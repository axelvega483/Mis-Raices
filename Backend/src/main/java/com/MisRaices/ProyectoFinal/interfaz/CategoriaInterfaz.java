package com.MisRaices.ProyectoFinal.interfaz;

import com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO.CategoriaGetDTO;
import com.MisRaices.ProyectoFinal.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaInterfaz {

    Categoria guardar(Categoria categoria);

    void eliminar(Integer id);

    Optional<CategoriaGetDTO> obtener(Integer id);

    List<CategoriaGetDTO> listar();

    Optional<Categoria> obtenerNombre(String nombre);

    boolean existeCategoria(Integer id);
}
