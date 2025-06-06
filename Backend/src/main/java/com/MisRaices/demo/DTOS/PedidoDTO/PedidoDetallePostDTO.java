package com.MisRaices.demo.DTOS.PedidoDTO;

import com.MisRaices.demo.DTOS.ProductoDTO.ProductoGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetallePostDTO {

    private ProductoGetDTO producto;
    private Integer cantidad;
}
