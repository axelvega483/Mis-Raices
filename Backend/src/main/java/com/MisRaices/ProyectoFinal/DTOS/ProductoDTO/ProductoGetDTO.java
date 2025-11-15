package com.MisRaices.ProyectoFinal.DTOS.ProductoDTO;

import com.MisRaices.ProyectoFinal.entity.Categoria;
import com.MisRaices.ProyectoFinal.util.ExposicionProducto;
import com.MisRaices.ProyectoFinal.util.OrigenProducto;
import com.MisRaices.ProyectoFinal.util.TamañoProducto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoGetDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private int stock;
    private String cuidado;
    private String video;
    private Double precio;
    private Categoria categoria;
    private String img;
    private ExposicionProducto exposicion;
    private TamañoProducto tamanio;
    private OrigenProducto origen;
}
