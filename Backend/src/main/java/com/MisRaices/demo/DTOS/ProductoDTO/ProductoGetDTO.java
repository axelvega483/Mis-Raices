package com.MisRaices.demo.DTOS.ProductoDTO;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.util.ExposicionProducto;
import com.MisRaices.demo.util.OrigenProducto;
import com.MisRaices.demo.util.TamañoProducto;
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
    private TamañoProducto tamano;
    private OrigenProducto origen;
}
