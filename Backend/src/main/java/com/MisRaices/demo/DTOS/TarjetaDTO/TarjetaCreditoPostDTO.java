package com.MisRaices.demo.DTOS.TarjetaDTO;

import com.MisRaices.demo.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCreditoPostDTO {

    @NotBlank
    private String titular;

    @NotBlank
    private String numero;

    @NotBlank
    private String fechaVencimiento;

    @NotBlank
    private String codigoSeguridad;

    @NotBlank
    private String tipo;

    @NotNull
    private Usuario usuario;
}
