package com.MisRaices.demo.DTOS.PedidoDTO;

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

        UsuarioSimpleDTO userDto = new UsuarioSimpleDTO();
        userDto.setId(pedido.getUsuario().getId());
        userDto.setNombre(pedido.getUsuario().getNombre());
        userDto.setCorreo(pedido.getUsuario().getCorreo());
        dto.setUsuario(userDto);

        List<PedidoDetalleDTO> detallesDto = pedido.getDetalle().stream().map(detalle -> {
            PedidoDetalleDTO detDto = new PedidoDetalleDTO();
            detDto.setId(detalle.getId());
            detDto.setCantidad(detalle.getCantidad());

            ProductoSimpleDTO prodDto = new ProductoSimpleDTO();
            prodDto.setId(detalle.getProducto().getId());
            prodDto.setNombre(detalle.getProducto().getNombre());
            prodDto.setPrecio(detalle.getProducto().getPrecio());

            detDto.setProducto(prodDto);
            return detDto;
        }).collect(Collectors.toList());

        dto.setDetalle(detallesDto);

        return dto;
    }
}
