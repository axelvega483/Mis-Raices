package com.MisRaices.demo.DTOS.UsuarioDTO;

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
    private Long telefono; // opcional
    @NotNull
    @Email
    private String correo;
    @NotNull
    private String password;
}
