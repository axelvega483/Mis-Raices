package com.MisRaices.demo.DTOS.PedidoDTO;

import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioGetDTO;
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
}
