package com.MisRaices.demo.DTOS.ProductoDTO;

import com.MisRaices.demo.entity.Producto;

public class ProductoMapper {

    public static ProductoGetDTO toDTO(Producto producto) {
        ProductoGetDTO dto = new ProductoGetDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setStock(producto.getStock());
        dto.setCuidado(producto.getCuidado());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        dto.setImg(producto.getImg());
        dto.setVideo(producto.getVideo());
        return dto;
    }
}
