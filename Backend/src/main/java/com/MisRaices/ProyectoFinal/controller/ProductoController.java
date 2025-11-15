package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.ProductoDTO.ProductoGetDTO;
import com.MisRaices.ProyectoFinal.interfaz.CategoriaInterfaz;
import com.MisRaices.ProyectoFinal.interfaz.ProductoInterfaz;
import com.MisRaices.ProyectoFinal.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Productos", description = "Controlador para operaciones de productos")
public class ProductoController {

    @Autowired
    private ProductoInterfaz productoService;

    @Autowired
    private CategoriaInterfaz categoriaService;

    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista con todos los productos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> listar() {
        List<ProductoGetDTO> dtos = productoService.listar();
        String message = dtos.isEmpty() ? "No hay productos registrados" : "Lista de productos obtenida exitosamente";
        return new ResponseEntity<>(new ApiRespo<>(message, dtos, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un producto por ID", description = "Busca y devuelve un producto específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespo<ProductoGetDTO>> obtenerProducto(
            @Parameter(description = "ID del producto", example = "1", required = true)
            @PathVariable Integer id) {
        Optional<ProductoGetDTO> producto = productoService.obtener(id);
        if (producto.isPresent()) {
            return new ResponseEntity<>(new ApiRespo<>("Producto encontrado", producto.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Producto no encontrado", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener productos por categoría", description = "Devuelve una lista de productos asociados a una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> obtenerProductoXcategoria(
            @Parameter(description = "ID de la categoría", example = "1", required = true)
            @PathVariable Integer id) {

        if (!categoriaService.existeCategoria(id)) {
            return new ResponseEntity<>(new ApiRespo<>("Categoría no encontrada", null, false), HttpStatus.NOT_FOUND);
        }

        List<ProductoGetDTO> productos = productoService.listarPorCategoria(id);
        String message = productos.isEmpty() ?
                "No hay productos para esta categoría" :
                "Productos por categoría encontrados";

        return new ResponseEntity<>(new ApiRespo<>(message, productos, true), HttpStatus.OK);
    }

    @Operation(summary = "Buscar productos por nombre", description = "Devuelve productos cuyo nombre coincida (total o parcialmente) con el valor ingresado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos con ese nombre"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiRespo<List<ProductoGetDTO>>> obtenerProductoNombre(
            @Parameter(description = "Nombre del producto a buscar", example = "Laptop", required = true)
            @PathVariable String nombre) {

        List<ProductoGetDTO> productos = productoService.obtenerNombre(nombre);

        if (productos.isEmpty()) {
            return new ResponseEntity<>(new ApiRespo<>("No se encontraron productos con ese nombre", productos, true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiRespo<>("Productos encontrados", productos, true), HttpStatus.OK);
    }
}