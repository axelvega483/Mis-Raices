package com.MisRaices.demo.DTOS.PedidoDTO;

import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.demo.util.EstadoPedido;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoGetDTO {

    private Integer id;
    private LocalDateTime fechaPedido;
    private EstadoPedido estado;
    private List<PedidoDetalleDTO> detalle;
    private Double total;
    private UsuarioGetDTO usuario;
}
