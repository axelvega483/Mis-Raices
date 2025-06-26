package com.example.misraices.data.model;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nombre;
    private String img;


    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", img=" + img + '}';
    }
}
