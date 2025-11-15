package com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTarjetas {

    private Integer id;
    private String titular;
    private String numero;
    private String fechaVencimiento;
    private String codigoSeguridad;
    private String tipo;
    private Double saldo;
}
