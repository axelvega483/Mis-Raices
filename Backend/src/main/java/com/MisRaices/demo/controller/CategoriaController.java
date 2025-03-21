package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.service.CategoriaService;
import com.MisRaices.demo.service.ImagenService;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("categoria")
public class CategoriaController {

    private Map<String, Object> response;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ImagenService imagenService;

    private final String RUTA_IMAGENES = System.getProperty("user.dir") + "/imagenes/";

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            response.put("categorias", categoriaService.listar());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar categoria", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtenerCategoria(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Categoria categoria = categoriaService.obtener(id).orElse(null);
            if (categoria != null) {
                String imgUrl = "http://localhost:8080/categoria/imagenes/" + categoria.getImg();
                categoria.setImg(imgUrl);
                response.put("categoria", categoria);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("categoria", "No encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/imagenes/{imgName}")
    public ResponseEntity<Resource> verImagene(@PathVariable String imgName) {
        try {
            Path imgPath = Paths.get(RUTA_IMAGENES).resolve(imgName);
            Resource resource = new UrlResource(imgPath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> crearCategoria(@RequestParam("nombreCategoria") String nombreCategoria,
            @RequestParam("img") MultipartFile imgFile) {
        try {
            Path directorioPath = Paths.get(RUTA_IMAGENES);
            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }
            String imgFileName = generarNombreUnico(imgFile.getOriginalFilename());
            Path imgPath = Paths.get(RUTA_IMAGENES, imgFileName);
            Files.copy(imgFile.getInputStream(), imgPath, StandardCopyOption.REPLACE_EXISTING);

            Categoria categoria = new Categoria();
            categoria.setNombre(nombreCategoria);
            categoria.setImg(imgFileName);

            categoriaService.guardar(categoria);

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
            Categoria categoria = categoriaService.obtener(id).orElse(null);
            if (categoria == null) {
                response.put("data", "No se encontró categoría");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            categoriaService.eliminar(categoria.getId());
            response.put("data", "Se eliminó la categoría id " + id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificar(@RequestBody Categoria categoria,
            @PathVariable(required = true) Integer id) {
        try {
            response = new HashMap<>();
            Categoria categoriaBd = categoriaService.obtener(id).orElse(null);
            if (categoriaBd == null) {
                response.put("data", "No se encontró categoría");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            actualizar(categoriaBd, categoria);

            response.put("data", categoriaService.guardar(categoriaBd));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HibernateException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void actualizar(Categoria viejo, Categoria nuevo) {
        if (nuevo.getImg() != null) {
            viejo.setImg(nuevo.getImg());
        }
        if (nuevo.getNombre() != null) {
            viejo.setNombre(nuevo.getNombre());
        }
    }

    private String generarNombreUnico(String originalFilename) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timestamp + "_" + originalFilename;
    }
}
