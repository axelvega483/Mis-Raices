package com.MisRaices.demo.DTOS.PedidoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDetalleDTO {

    private Integer id;
    private Integer cantidad;
    private ProductoSimpleDTO producto;

}
