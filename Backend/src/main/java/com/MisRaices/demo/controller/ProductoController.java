package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.ProductoDTO.ProductoGetDTO;
import com.MisRaices.demo.DTOS.ProductoDTO.ProductoMapper;
import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ProductoService;
import com.MisRaices.demo.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista con todos los productos registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> listar() {
        try {
            List<ProductoGetDTO> dtos = productoService.listar().stream()
                    .map(ProductoMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiRespo<>("Lista de productos obtenida exitosamente", dtos, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno al buscar por nombre: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un producto por ID", description = "Busca y devuelve un producto específico según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<ApiRespo<ProductoGetDTO>> obtenerProducto(
            @Parameter(description = "ID del producto", required = true) @PathVariable Integer id) {

        try {
            Producto producto = productoService.obtener(id).orElse(null);
            if (producto != null) {
                ProductoGetDTO dto = ProductoMapper.toDTO(producto);
                return new ResponseEntity<>(new ApiRespo<>("Producto encontrado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Producto no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno al buscar por nombre: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener productos por categoría", description = "Devuelve una lista de productos asociados a una categoría específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "404", description = "Categoría o productos no encontrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("categoria/{id}")
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> obtenerProductoXcategoria(
            @Parameter(description = "ID de la categoría", required = true) @PathVariable Integer id) {

        try {
            Categoria categoria = categoriaService.obtener(id).orElse(null);
            if (categoria == null) {
                return new ResponseEntity<>(new ApiRespo<>("Categoría no encontrada", null, false), HttpStatus.NOT_FOUND);
            }

            List<Producto> productos = productoService.listarPorCategoria(categoria);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(new ApiRespo<>("No hay productos para esta categoría", null, false), HttpStatus.NOT_FOUND);
            }

            List<ProductoGetDTO> dtos = productos.stream()
                    .map(ProductoMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiRespo<>("Productos por categoría encontrados", dtos, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno al buscar por nombre: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Buscar productos por nombre", description = "Devuelve productos cuyo nombre coincida (total o parcialmente) con el valor ingresado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con ese nombre"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> obtenerProductoNombre(
            @Parameter(description = "Nombre del producto a buscar", required = true) @PathVariable String nombre) {

        try {
            List<Producto> productos = productoService.obtenerNombre(nombre);

            if (productos != null && !productos.isEmpty()) {
                List<ProductoGetDTO> dtos = productos.stream()
                        .map(ProductoMapper::toDTO)
                        .collect(Collectors.toList());

                return new ResponseEntity<>(new ApiRespo<>("Productos encontrados", dtos, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("No se encontraron productos con el nombre: " + nombre, null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno al buscar por nombre: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
