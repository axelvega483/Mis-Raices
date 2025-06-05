package com.MisRaices.demo.DTOS.PedidoDTO;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoGetDTO {

    private Integer id;
    private LocalDateTime fechaPedido;
    private String estado;
    private List<PedidoDetalleDTO> detalle;
    private Double total;
    private UsuarioSimpleDTO usuario;
}
