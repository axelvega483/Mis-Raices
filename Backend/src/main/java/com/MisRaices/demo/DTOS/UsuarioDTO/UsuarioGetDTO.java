package com.MisRaices.demo.DTOS.UsuarioDTO;

import com.MisRaices.demo.entity.Direccion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioGetDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String correo;
    private String password;
    private boolean activo;
    private String codigo;
    private String token;
    private LocalDateTime tokenLimite;
    private Direccion direccion;
    @JsonIgnore
    private List<UsuarioPedidos> pedidos;
    @JsonIgnore
    private List<UsuarioTarjetas> tarjetas;
}
