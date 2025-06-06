package com.MisRaices.demo.DTOS.TarjetaDTO;

import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.demo.entity.TarjetaCredito;

public class TarjetaMapper {

    public static TarjetaCreditoGetDTO toDTO(TarjetaCredito tarjeta) {
        TarjetaCreditoGetDTO dto = new TarjetaCreditoGetDTO();
        dto.setId(tarjeta.getId());
        dto.setFechaVencimiento(tarjeta.getFechaVencimiento());
        dto.setSaldo(tarjeta.getSaldo());
        dto.setTipo(tarjeta.getTipo());
        dto.setTitular(tarjeta.getTitular());
        dto.setNumero(tarjeta.getNumero());
        dto.setCodigoSeguridad(tarjeta.getCodigoSeguridad());
        if (tarjeta.getUsuario() != null) {
            dto.setUsuario(UsuarioMapper.toDTO(tarjeta.getUsuario()));
        }

        return dto;
    }

}
