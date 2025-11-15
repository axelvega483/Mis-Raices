package com.MisRaices.ProyectoFinal.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private String calle;
    private Long numero;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private Double latitud;
    private Double longitud;
}
