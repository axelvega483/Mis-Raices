package com.MisRaices.demo.DTOS.UsuarioDTO;

import com.MisRaices.demo.util.EstadoPedido;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPedidos {

    private Integer id;
    private LocalDateTime fechaPedido;
    private EstadoPedido estado;
    private Double total;
}
