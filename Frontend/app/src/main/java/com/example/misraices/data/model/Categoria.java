package com.example.misraices.data.model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private Integer id;
    private String nombre;
    private String img;

    public Categoria() {
    }

    public Categoria(String nombre, String img) {
        this.nombre = nombre;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", img=" + img + '}';
    }
}
