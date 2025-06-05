package com.MisRaices.demo.DTOS.TarjetaDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaCreditoGetDTO {

    private Integer id;
    private String titular;
    private String fechaVencimiento;
    private String tipo;
    private Double saldo;
    private Integer usuarioId;
    private String usuarioNombre;
    private String usuarioCorreo;
}
