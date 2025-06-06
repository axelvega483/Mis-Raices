package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.CategoriaDTO.CategoriaGetDTO;
import com.MisRaices.demo.DTOS.CategoriaDTO.CategoriaMapper;
import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
            summary = "Listar categorías",
            description = "Devuelve una lista de todas las categorías disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Categorías listadas correctamente")
    @GetMapping
    public ResponseEntity<ApiRespo<?>> listar() {
        try {
            List<CategoriaGetDTO> dto = categoriaService.listar()
                    .stream()
                    .map(CategoriaMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiRespo<>("Categoria", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: "+e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Obtener categoría por ID",
            description = "Busca una categoría en la base de datos a partir de su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<ApiRespo<?>> obtenerCategoria(
            @Parameter(description = "ID de la categoría a buscar") @PathVariable Integer id) {
        try {

            Categoria categoria = categoriaService.obtener(id).orElse(null);
            if (categoria != null) {
                CategoriaGetDTO dto = CategoriaMapper.toDTO(categoria);
                return new ResponseEntity<>(new ApiRespo<>("Categoria encontrada", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Categoria no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: "+e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Obtener categoría por nombre",
            description = "Busca una categoría en la base de datos a partir de su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<ApiRespo<?>> obtenerCategoriaPorNombre(
            @Parameter(description = "Nombre de la categoría a buscar") @PathVariable String nombre) {
        try {

            Categoria categoria = categoriaService.obtenerNombre(nombre).orElse(null);
            if (categoria != null) {
                CategoriaGetDTO dto = CategoriaMapper.toDTO(categoria);
                return new ResponseEntity<>(new ApiRespo<>("Categoria encontrada", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Categoria no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: "+e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
