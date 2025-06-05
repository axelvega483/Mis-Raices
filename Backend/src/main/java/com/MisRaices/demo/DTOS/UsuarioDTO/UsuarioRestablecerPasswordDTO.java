package com.MisRaices.demo.DTOS.UsuarioDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRestablecerPasswordDTO {

    @NotNull
    private String token;
    @NotNull
    private String password;
}
