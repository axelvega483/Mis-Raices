package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ImagenService;
import com.MisRaices.demo.service.ProductoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("producto")
public class ProductoController {

    private Map<String, Object> response;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService CategoriaService;

    @Autowired
    private ImagenService imagenService;
    private final String RUTA_IMAGENES = System.getProperty("user.dir") + "/imagenes/";

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            response.put("productos", productoService.listar());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar productos", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtenerCategoria(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Producto producto = productoService.obtener(id).orElse(null);
            if (producto != null) {
                String imgUrl = "http://localhost:8080/categoria/imagenes/" + producto.getImg();
                producto.setImg(imgUrl); 
                response.put("producto", producto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("producto", "No encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> crearProducto(@RequestParam("producto") String producto,
            @RequestParam("img") MultipartFile imgFile, 
            @RequestParam("categoria") Integer id,
            @RequestParam("descripcion")String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") Integer stock) {
        try {

            String imgFileName = generarNombreUnico(imgFile.getOriginalFilename());

            Path imgPath = Paths.get(RUTA_IMAGENES, imgFileName);
            Files.copy(imgFile.getInputStream(), imgPath, StandardCopyOption.REPLACE_EXISTING);
            Categoria categoria = CategoriaService.obtener(id).get();

            if (categoria != null) {
                Producto pro = new Producto();
                pro.setNombre(producto);
                pro.setDescripcion(descripcion);
                pro.setPrecio(precio);
                pro.setStock(stock);
                pro.setImg(imgFileName);
                pro.setCategoria(categoria);
                productoService.guardar(pro);
            }

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Producto producto = productoService.obtener(id).orElse(null);
            if (producto == null) {
                response.put("data", "No se encontró producto");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            productoService.eliminar(producto.getId());
            response.put("data", "Se eliminó el producto id " + id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@RequestBody Producto producto,
            @PathVariable(required = true) Integer id) {
        try {
            response = new HashMap<>();
            Producto productoBD = productoService.obtener(id).orElse(null);
            if (productoBD == null) {
                response.put("data", "No se encontró producto");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            actualizar(productoBD, producto);

            response.put("data", productoService.guardar(productoBD));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HibernateException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void actualizar(Producto viejo, Producto nuevo) {
        if (nuevo.getImg() != null) {
            viejo.setImg(nuevo.getImg());
        }
        if (nuevo.getNombre() != null) {
            viejo.setNombre(nuevo.getNombre());
        }
        if (nuevo.getCategoria() != null) {
            viejo.setCategoria(nuevo.getCategoria());
        }
        if (nuevo.getDescripcion() != null) {
            viejo.setDescripcion(nuevo.getDescripcion());
        }
        if (nuevo.getPrecio() != null) {
            viejo.setPrecio(nuevo.getPrecio());
        }
        if (nuevo.getStock() != null) {
            viejo.setStock(nuevo.getStock());
        }

    }

    private String generarNombreUnico(String originalFilename) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timestamp + "_" + originalFilename;
    }

}
