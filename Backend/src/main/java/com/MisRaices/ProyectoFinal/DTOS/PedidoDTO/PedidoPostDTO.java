package com.MisRaices.ProyectoFinal.DTOS.PedidoDTO;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.ProyectoFinal.util.EstadoPedido;
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

    private UsuarioGetDTO usuario;
    private List<PedidoDetallePostDTO> detalle;
    private EstadoPedido estado;
}
