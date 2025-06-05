package com.MisRaices.demo.DTOS.TarjetaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCreditoPutDTO {

    private Integer id;
    private String titular;
    private String numero;
    private String fechaVencimiento;
    private String codigoSeguridad;
    private String tipo;
}
