package com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO;

import com.MisRaices.ProyectoFinal.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class UsuarioMapper {

    public UsuarioGetDTO toDTO(Usuario usuario) {
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

    public Usuario toEntity(UsuarioRegistroDTO registroDTO) {
        String activationCode = generateActivationCode();
        Usuario usuario = new Usuario();
        usuario.setActivo(false);
        usuario.setCodigo(activationCode);
        usuario.setApellido(registroDTO.getApellido());
        usuario.setCorreo(registroDTO.getCorreo());
        usuario.setId(registroDTO.getId());
        usuario.setNombre(registroDTO.getNombre());
        usuario.setPassword(registroDTO.getPassword());
        usuario.setTelefono(registroDTO.getTelefono());
        return usuario;
    }

    public Usuario updateEntity(Usuario user, UsuarioPutDTO put) {
        user.setApellido(put.getApellido());
        user.setNombre(put.getNombre());
        user.setPassword(put.getPassword());
        if (put.getDireccion() != null) {
            if (user.getDireccion() != null) {
                DireccionDTO d = put.getDireccion();
                user.getDireccion().setCalle(d.getCalle());
                user.getDireccion().setNumero(d.getNumero());
                user.getDireccion().setCiudad(d.getCiudad());
                user.getDireccion().setProvincia(d.getProvincia());
                user.getDireccion().setCodigoPostal(d.getCodigoPostal());
                user.getDireccion().setLatitud(d.getLatitud());
                user.getDireccion().setLongitud(d.getLongitud());
            }
        }
        return user;
    }
    public String generateActivationCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();

        StringBuilder activationCode = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            activationCode.append(letters.charAt(random.nextInt(letters.length())));
        }
        for (int i = 0; i < 3; i++) {
            activationCode.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return activationCode.toString();
    }
}
