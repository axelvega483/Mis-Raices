package com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionDTO {
    private String calle;
    private Long numero;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private Double latitud;
    private Double longitud;
}
