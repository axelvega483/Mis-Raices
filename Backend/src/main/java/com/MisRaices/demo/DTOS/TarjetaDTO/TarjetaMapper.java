/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MisRaices.demo.DTOS.TarjetaDTO;

import com.MisRaices.demo.entity.TarjetaCredito;

public class TarjetaMapper {

    public static TarjetaCreditoGetDTO toDTO(TarjetaCredito tarjeta) {
        TarjetaCreditoGetDTO dto = new TarjetaCreditoGetDTO();
        dto.setId(tarjeta.getId());
        dto.setFechaVencimiento(tarjeta.getFechaVencimiento());
        dto.setSaldo(tarjeta.getSaldo());
        dto.setTipo(tarjeta.getTitular());
        dto.setTitular(tarjeta.getTitular());
        if (tarjeta.getUsuario() != null) {
            dto.setUsuarioId(tarjeta.getUsuario().getId());
            dto.setUsuarioNombre(tarjeta.getUsuario().getNombre());
            dto.setUsuarioCorreo(tarjeta.getUsuario().getCorreo());
        }

        return dto;
    }

}
