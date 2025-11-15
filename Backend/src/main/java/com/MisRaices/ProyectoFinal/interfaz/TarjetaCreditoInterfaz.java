package com.MisRaices.ProyectoFinal.interfaz;

import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPostDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPutDTO;

import java.util.List;
import java.util.Optional;

public interface TarjetaCreditoInterfaz {

    TarjetaCreditoGetDTO crear(TarjetaCreditoPostDTO postDTO);

    void eliminar(Integer id);

    Optional<TarjetaCreditoGetDTO> obtener(Integer id);

    List<TarjetaCreditoGetDTO> listar();

    TarjetaCreditoGetDTO actualizar(Integer id, TarjetaCreditoPutDTO putDTO);
}
