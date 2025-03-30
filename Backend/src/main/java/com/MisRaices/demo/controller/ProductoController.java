package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ProductoService;
import java.util.HashMap;
import java.util.List;
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
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>(productoService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar productos", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Integer id) {
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

    @GetMapping("categoria/{id}")
    public ResponseEntity<?> obtenerProductoXcategoria(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Categoria categoria = categoriaService.obtener(id).orElse(null);

            if (categoria != null) {
                // Filtrar productos que pertenecen a la categoría
                List<Producto> productosPorCategoria = productoService.listarPorCategoria(categoria);

                if (!productosPorCategoria.isEmpty()) {
                    return new ResponseEntity<>(productosPorCategoria, HttpStatus.OK);
                } else {
                    response.put("producto", "No productos encontrados para esta categoría");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                response.put("categoria", "Categoría no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> obtenerProductoNombre(@PathVariable String nombre) {
        try {
            List<Producto> productos = productoService.obtenerNombre(nombre);

        if (productos != null && !productos.isEmpty()) {
            // Si se encuentran productos, devolver la lista
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } else {
            // Si no se encuentran productos, devolver un mensaje adecuado
            response.put("message", "No se encontraron productos con el nombre: " + nombre);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
