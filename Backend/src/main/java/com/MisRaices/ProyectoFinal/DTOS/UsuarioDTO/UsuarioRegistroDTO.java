package com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroDTO {

    @NotNull
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    private Long telefono;
    @NotNull
    @Email
    private String correo;
    @NotNull
    private String password;
}
