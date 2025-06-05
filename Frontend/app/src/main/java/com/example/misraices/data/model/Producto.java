package com.example.misraices.data.model;

import java.io.Serializable;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {
    private Integer id;
    private String nombre;
    private String descripcion;
    private int stock;
    private Double precio;
    private String cuidado;
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
                ", categoria=" + categoria +
                ", img='" + img + '\'' +
                '}';
    }
}
