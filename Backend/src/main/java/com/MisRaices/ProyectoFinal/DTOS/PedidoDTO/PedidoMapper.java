package com.MisRaices.ProyectoFinal.DTOS.PedidoDTO;

import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoMapper;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.ProyectoFinal.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private ProductoMapper productoMapper;

    public PedidoGetDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoGetDTO dto = new PedidoGetDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());
        if (pedido.getUsuario() != null) {
            dto.setUsuario(usuarioMapper.toDTO(pedido.getUsuario()));
        }

        List<PedidoDetalleDTO> detallesDto = pedido.getDetalle().stream().map(detalle -> {
            PedidoDetalleDTO detDto = new PedidoDetalleDTO();
            detDto.setId(detalle.getId());
            detDto.setCantidad(detalle.getCantidad());
            if (detalle.getProducto() != null) {
                detDto.setProducto(productoMapper.toDTO(detalle.getProducto()));
            }
            return detDto;
        }).collect(Collectors.toList());

        dto.setDetalle(detallesDto);

        return dto;
    }

    public List<PedidoGetDTO> dtoList(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toDTO).toList();
    }
}
