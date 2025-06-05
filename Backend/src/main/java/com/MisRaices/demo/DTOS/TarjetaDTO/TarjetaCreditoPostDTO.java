package com.MisRaices.demo.DTOS.TarjetaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCreditoPostDTO {

    @NonNull
    private Integer id;
    @NonNull
    private String titular;
    @NonNull
    private String numero;
    @NonNull
    private String fechaVencimiento;
    @NonNull
    private String codigoSeguridad;
    @NonNull
    private String tipo;
    private Integer usuario;
}
