package com.MisRaices.demo.DTOS.CategoriaDTO;

import com.MisRaices.demo.entity.Categoria;


public class CategoriaMapper {
    public static CategoriaGetDTO toDTO(Categoria categoria){
        CategoriaGetDTO dto= new CategoriaGetDTO();
        dto.setId(categoria.getId());
        dto.setImg(categoria.getImg());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
}
