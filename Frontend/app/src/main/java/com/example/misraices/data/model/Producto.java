package com.example.misraices.data.model;

import com.example.misraices.data.util.ExposicionProducto;
import com.example.misraices.data.util.OrigenProducto;
import com.example.misraices.data.util.TamañoProducto;

import java.io.Serializable;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nombre;
    private String descripcion;
    private int stock;
    private Double precio;
    private String cuidado;
    private ExposicionProducto exposicion;
    private TamañoProducto tamano;
    private OrigenProducto origen;
    private String video;
    private Categoria categoria;
    private String img;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", cuidados='" + cuidado + '\'' +
                ", video='" + video + '\'' +
                ", categoria=" + categoria +
                ", img='" + img + '\'' +
                '}';
    }
}
