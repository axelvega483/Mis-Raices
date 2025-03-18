/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    Map<String, Object> response;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> listarTodos() {
        try {
            response = new HashMap<>();
            response.put("Usuarios", usuarioService.listar());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("sin Usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                response.put("Usuario", user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("Usuario no encontrado", user);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("sin Usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.guardar(usuario);
            response.put("usuario Guardado", user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error al crear usuario", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> modificarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                actualizar(user, usuario);
                response.put("usuario actualizado", usuarioService.guardar(user));
                return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Integer id) {
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
        if (nuevo.getPassword() != null) {
            viejo.setPassword(nuevo.getPassword());
        }    
        if(nuevo.getCorreo()==null){
            viejo.setCorreo(viejo.getCorreo());
        }else{
            viejo.setCorreo(nuevo.getCorreo());
        }
        
    }

}
