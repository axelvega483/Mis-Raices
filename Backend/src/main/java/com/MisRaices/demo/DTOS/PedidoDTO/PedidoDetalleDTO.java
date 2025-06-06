package com.MisRaices.demo.DTOS.PedidoDTO;

import com.MisRaices.demo.DTOS.ProductoDTO.ProductoGetDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDetalleDTO {

    private Integer id;
    private Integer cantidad;
    private ProductoGetDTO producto;

}
