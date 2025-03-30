package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.UsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    Map<String, Object> response;

    @GetMapping()
    public ResponseEntity<?> listarTodos() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("sin Usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                response.put("Usuario no encontrado", user);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("sin Usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                actualizar(user, usuario);
                return new ResponseEntity<>(usuarioService.guardar(user), HttpStatus.OK);
            } else {
                response.put("usuario no encontrado", user);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("error al actualizar usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user == null) {
                response.put("no existe usuario para eliminar", user);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            usuarioService.eliminar(id);
            response.put("usuario eliminado", "");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al eliminar el usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void actualizar(Usuario viejo, Usuario nuevo) {
        if (nuevo.getNombre() != null) {
            viejo.setNombre(nuevo.getNombre());
        }
        if (nuevo.getApellido() != null) {
            viejo.setApellido(nuevo.getApellido());
        }
        if (nuevo.getTelefono() != null) {
            viejo.setTelefono(nuevo.getTelefono());
        }
        if (nuevo.getImgUri() != null) {
            viejo.setImgUri(nuevo.getImgUri());
        }
    }

}
