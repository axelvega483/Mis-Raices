package com.MisRaices.ProyectoFinal.DTOS.PedidoDTO;

import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoGetDTO;
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
