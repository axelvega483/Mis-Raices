package com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioGetDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaCreditoGetDTO {

    private Integer id;
    private String titular;
    private String numero;
    private String fechaVencimiento;
    private String codigoSeguridad;
    private String tipo;
    private Double saldo;
    private UsuarioGetDTO usuario;
}
