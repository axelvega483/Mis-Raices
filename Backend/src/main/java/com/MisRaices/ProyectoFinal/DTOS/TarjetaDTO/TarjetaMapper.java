package com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.ProyectoFinal.entity.TarjetaCredito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarjetaMapper {
    @Autowired
    private UsuarioMapper usuarioMapper;

    public TarjetaCreditoGetDTO toDTO(TarjetaCredito tarjeta) {
        TarjetaCreditoGetDTO dto = new TarjetaCreditoGetDTO();
        dto.setId(tarjeta.getId());
        dto.setFechaVencimiento(tarjeta.getFechaVencimiento());
        dto.setSaldo(tarjeta.getSaldo());
        dto.setTipo(tarjeta.getTipo());
        dto.setTitular(tarjeta.getTitular());
        dto.setNumero(tarjeta.getNumero());
        dto.setCodigoSeguridad(tarjeta.getCodigoSeguridad());
        if (tarjeta.getUsuario() != null) {
            dto.setUsuario(usuarioMapper.toDTO(tarjeta.getUsuario()));
        }

        return dto;
    }

    public TarjetaCredito toEntity(TarjetaCreditoPostDTO postDTO) {
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setFechaVencimiento(postDTO.getFechaVencimiento());
        tarjeta.setTipo(postDTO.getTipo());
        tarjeta.setTitular(postDTO.getTitular());
        tarjeta.setNumero(postDTO.getNumero());
        tarjeta.setCodigoSeguridad(postDTO.getCodigoSeguridad());
        return tarjeta;
    }

    public TarjetaCredito actualizar(TarjetaCredito tarjeta, TarjetaCreditoPutDTO putDTO) {
        tarjeta.setFechaVencimiento(putDTO.getFechaVencimiento());
        tarjeta.setTipo(putDTO.getTipo());
        tarjeta.setTitular(putDTO.getTitular());
        tarjeta.setNumero(putDTO.getNumero());
        tarjeta.setCodigoSeguridad(putDTO.getCodigoSeguridad());
        return tarjeta;
    }

    public List<TarjetaCreditoGetDTO> dtoList(List<TarjetaCredito> tarjetaCreditos) {
        return tarjetaCreditos.stream().map(this::toDTO).toList();
    }
}
