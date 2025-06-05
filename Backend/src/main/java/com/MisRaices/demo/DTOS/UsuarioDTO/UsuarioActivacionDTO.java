package com.MisRaices.demo.DTOS.UsuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class UsuarioActivacionDTO {
     @NotNull @Email
    private String correo;
    @NotNull
    private String codigo;
}
