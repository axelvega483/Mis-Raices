package com.MisRaices.ProyectoFinal.DTOS.ProductoDTO;

import com.MisRaices.ProyectoFinal.entity.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {

    public ProductoGetDTO toDTO(Producto producto) {
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
        dto.setExposicion(producto.getExposicion());
        dto.setTamanio(producto.getTamanio());
        dto.setOrigen(producto.getOrigen());
        return dto;
    }
    public List<ProductoGetDTO>dtoList(List<Producto> productos){
        return productos.stream().map(this::toDTO).toList();
    }
}
