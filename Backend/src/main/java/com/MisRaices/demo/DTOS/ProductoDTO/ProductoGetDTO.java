package com.MisRaices.demo.DTOS.ProductoDTO;

import com.MisRaices.demo.entity.Categoria;
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

}
