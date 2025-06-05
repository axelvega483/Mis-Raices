package com.MisRaices.demo.DTOS.PedidoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetallePostDTO {

    private Integer productoId;
    private Integer cantidad;
}
