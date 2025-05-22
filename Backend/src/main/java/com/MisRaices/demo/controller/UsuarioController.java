package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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

    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve un usuario específico según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerUsuario(
            @Parameter(description = "ID del usuario a obtener", required = true) @PathVariable Integer id) {
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

    @Operation(summary = "Modificar un usuario", description = "Actualiza los datos de un usuario existente mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> modificarUsuario(
            @Parameter(description = "Datos actualizados del usuario", required = true)
            @RequestBody Usuario usuario,
            @Parameter(description = "ID del usuario a modificar", required = true) @PathVariable Integer id) {
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

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Integer id) {
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
        if (nuevo.getDireccion() != null) {
            viejo.setDireccion(nuevo.getDireccion());
        }
        if (nuevo.getPassword() != null) {
            viejo.setPassword(nuevo.getPassword());
        }
    }

}
