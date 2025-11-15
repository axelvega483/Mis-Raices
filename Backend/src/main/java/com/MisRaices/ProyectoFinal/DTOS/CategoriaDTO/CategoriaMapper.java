package com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO;

import com.MisRaices.ProyectoFinal.entity.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaMapper {
    public CategoriaGetDTO toDTO(Categoria categoria) {
        CategoriaGetDTO dto = new CategoriaGetDTO();
        dto.setId(categoria.getId());
        dto.setImg(categoria.getImg());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
    public List<CategoriaGetDTO> toDTOList(List<Categoria> categorias) {
        return categorias.stream().map(this::toDTO).toList();
    }
}
