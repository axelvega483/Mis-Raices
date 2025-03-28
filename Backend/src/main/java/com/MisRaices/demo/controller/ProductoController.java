package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ProductoService;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String, Object> response;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService CategoriaService;


    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>( productoService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar productos", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Producto producto = productoService.obtener(id).orElse(null);
            if (producto != null) {
                return new ResponseEntity<>(producto, HttpStatus.OK);
            } else {
                response.put("producto", "No encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
