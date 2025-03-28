package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.service.CategoriaService;
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

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Integer id) {
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

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> obtenerCategoriaPorNombre(@PathVariable String nombre) {
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
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
