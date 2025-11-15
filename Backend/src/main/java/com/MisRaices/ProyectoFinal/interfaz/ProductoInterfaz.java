package com.MisRaices.ProyectoFinal.interfaz;

import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoGetDTO;
import com.MisRaices.ProyectoFinal.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoInterfaz {

    Producto guardar(Producto producto);

    Optional<ProductoGetDTO> obtener(Integer id);

    List<ProductoGetDTO> listar();

    List<ProductoGetDTO> listarPorCategoria(Integer categoriaId);

    List<ProductoGetDTO> obtenerNombre(String nombre);
}
