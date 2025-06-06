package com.MisRaices.demo.DTOS.PedidoDTO;

import com.MisRaices.demo.DTOS.ProductoDTO.ProductoMapper;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.demo.entity.Pedido;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoGetDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoGetDTO dto = new PedidoGetDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());
        if (pedido.getUsuario() != null) {
            dto.setUsuario(UsuarioMapper.toDTO(pedido.getUsuario()));
        }

        List<PedidoDetalleDTO> detallesDto = pedido.getDetalle().stream().map(detalle -> {
            PedidoDetalleDTO detDto = new PedidoDetalleDTO();
            detDto.setId(detalle.getId());
            detDto.setCantidad(detalle.getCantidad());
            if (detalle.getProducto() != null) {
                detDto.setProducto(ProductoMapper.toDTO(detalle.getProducto()));
            }
            return detDto;
        }).collect(Collectors.toList());

        dto.setDetalle(detallesDto);

        return dto;
    }
}
