package com.MisRaices.demo.DTOS.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPutDTO {

    private String nombre;
    private String apellido;
    private Long telefono;
    private String password;
    private DireccionDTO direccion;
}
