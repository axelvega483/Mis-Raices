package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.service.CategoriaService;
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

    private Map<String, Object> response;
    @Autowired
    private CategoriaService categoriaService;

    @Operation(
            summary = "Listar categorías",
            description = "Devuelve una lista de todas las categorías disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Categorías listadas correctamente")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>(categoriaService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar categoria", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> obtenerCategoria(
            @Parameter(description = "ID de la categoría a buscar") @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Categoria categoria = categoriaService.obtener(id).orElse(null);
            if (categoria != null) {
                return new ResponseEntity<>(categoria, HttpStatus.OK);
            } else {
                response.put("categoria", "No encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> obtenerCategoriaPorNombre(
            @Parameter(description = "Nombre de la categoría a buscar") @PathVariable String nombre) {
        try {
            response = new HashMap<>();
            Categoria categoria = categoriaService.obtenerNombre(nombre).orElse(null);
            if (categoria != null) {
                return new ResponseEntity<>(categoria, HttpStatus.OK);
            } else {
                response.put("categoria", "no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
