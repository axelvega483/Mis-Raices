package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO.CategoriaGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.CategoriaDTO.CategoriaMapper;
import com.MisRaices.ProyectoFinal.entity.Categoria;
import com.MisRaices.ProyectoFinal.interfaz.CategoriaInterfaz;
import com.MisRaices.ProyectoFinal.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("categoria")
@Tag(name = "Categorías", description = "Controlador para operaciones de categorías")
public class CategoriaController {

    @Autowired
    private CategoriaInterfaz categoriaService;

    @Operation(summary = "Listar categorías", description = "Devuelve una lista de todas las categorías disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías listadas correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay categorías registradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiRespo<List<CategoriaGetDTO>>> listar() {
        List<CategoriaGetDTO> dto = categoriaService.listar();
        String message = dto.isEmpty() ? "No hay categorías registradas" : "Categorías listadas correctamente";
        return new ResponseEntity<>(new ApiRespo<>(message, dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Busca una categoría en la base de datos a partir de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespo<CategoriaGetDTO>> obtenerCategoria(
            @Parameter(description = "ID de la categoría a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        return categoriaService.obtener(id)
                .map(categoria -> new ResponseEntity<>(new ApiRespo<>("Categoría encontrada", categoria, true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiRespo<>("Categoría no encontrada", null, false), HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Obtener categoría por nombre", description = "Busca una categoría en la base de datos a partir de su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiRespo<CategoriaGetDTO>> obtenerCategoriaPorNombre(
            @Parameter(description = "Nombre de la categoría a buscar", example = "Electrónicos", required = true)
            @PathVariable String nombre) {
        Optional<Categoria> categoria = categoriaService.obtenerNombre(nombre);
        CategoriaGetDTO dto = categoria.map(cat -> new CategoriaMapper().toDTO(cat)).orElse(null);

        if (dto != null) {
            return new ResponseEntity<>(new ApiRespo<>("Categoría encontrada", dto, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Categoría no encontrada", null, false), HttpStatus.NOT_FOUND);
        }
    }
}