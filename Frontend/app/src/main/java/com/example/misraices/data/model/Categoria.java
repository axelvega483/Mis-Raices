package com.example.misraices.data.model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private Integer id;
    private String nombre;
    private String imgUri;

    public Categoria() {
    }

    public Categoria(String nombre, String imgUri) {
        this.nombre = nombre;
        this.imgUri = imgUri;
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

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", imgUri=" + imgUri + '}';
    }
}
