package com.MisRaices.demo.DTOS.PedidoDTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPostDTO {

    private Integer usuarioId;
    private List<PedidoDetallePostDTO> detalle;
}
