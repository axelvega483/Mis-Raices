package com.MisRaices.demo.DTOS.UsuarioDTO;

import com.MisRaices.demo.entity.Usuario;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsuarioMapper {

    public static UsuarioGetDTO toDTO(Usuario usuario) {
        UsuarioGetDTO dto = new UsuarioGetDTO();
        dto.setActivo(usuario.isActivo());
        dto.setApellido(usuario.getApellido());
        dto.setCodigo(usuario.getCodigo());
        dto.setCorreo(usuario.getCorreo());
        dto.setDireccion(usuario.getDireccion());
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setPassword(usuario.getPassword());
        dto.setTelefono(usuario.getTelefono());
        dto.setToken(usuario.getToken());
        dto.setTokenLimite(usuario.getTokenLimite());

        List<UsuarioPedidos> pedidos = Optional.ofNullable(usuario.getPedidos()).orElse(Collections.emptyList())
                .stream()
                .map(pedido -> new UsuarioPedidos(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstado(),
                pedido.getTotal()))
                .toList();

        List<UsuarioTarjetas> tarjetas = Optional.ofNullable(usuario.getTarjetas()).orElse(Collections.emptyList())
                .stream()
                .map(tarjeta -> new UsuarioTarjetas(tarjeta.getId(),
                tarjeta.getTitular(),
                tarjeta.getNumero(),
                tarjeta.getFechaVencimiento(),
                tarjeta.getCodigoSeguridad(),
                tarjeta.getTipo(),
                tarjeta.getSaldo()))
                .toList();
        dto.setPedidos(pedidos);
        dto.setTarjetas(tarjetas);
        return dto;
    }
}
