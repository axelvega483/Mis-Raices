package com.MisRaices.ProyectoFinal.interfaz;

import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoPostDTO;
import com.MisRaices.ProyectoFinal.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoInterfaz {

    PedidoGetDTO crear(PedidoPostDTO postDTO);

    Optional<PedidoGetDTO> obtener(Integer id);

    List<PedidoGetDTO> listar();

    PedidoGetDTO finalizarCompra(Integer pedidoId, Integer tarjetaId);

    Optional<Pedido> obtenerEntity(Integer id);
}
