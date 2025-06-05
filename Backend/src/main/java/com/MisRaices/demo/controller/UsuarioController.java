package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioPutDTO;
import com.MisRaices.demo.entity.Direccion;
import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.UsuarioService;
import com.MisRaices.demo.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<?> listarTodos() {
        try {
            List<UsuarioGetDTO> dto = usuarioService.listar().stream()
                    .map(UsuarioMapper::toDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiRespo<>("Usuarios", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
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

            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                UsuarioGetDTO dto = UsuarioMapper.toDTO(user);
                return new ResponseEntity<>(new ApiRespo<>("Usuario", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado", null, true), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
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
            @RequestBody UsuarioPutDTO usuarioPut,
            @Parameter(description = "ID del usuario a modificar", required = true) @PathVariable Integer id) {
        try {

            Usuario user = usuarioService.obtener(id).orElse(null);
            if (user != null) {
                user.setApellido(usuarioPut.getApellido());
                user.setNombre(usuarioPut.getNombre());
                if (user.getPassword().equals(usuarioPut.getPassword())) {
                    user.setPassword(usuarioPut.getPassword());
                    UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(user));
                    return new ResponseEntity<>(new ApiRespo<>("Usuario actualizado", dto, true), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiRespo<>("Error, contraseña no coiciden", null, false), HttpStatus.CONFLICT);
                }
            } else {
                return new ResponseEntity<>(new ApiRespo<>("usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error al eliminar usuario: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar o actualizar dirección del usuario", description = "Permite asignar o actualizar la dirección embebida de un usuario existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/direccion/{id}")
    public ResponseEntity<?> cargarDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        try {
            Usuario usuario = usuarioService.obtener(id).orElse(null);
            if (usuario != null) {
                usuario.setDireccion(direccion);
                UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(usuario));
                return new ResponseEntity<>(new ApiRespo<>("Dirección actualizada correctamente", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error al actualizar dirección: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.obtener(id).orElse(null);
            if (usuario != null) {
                usuarioService.eliminar(id);
                return new ResponseEntity<>(new ApiRespo<>("Usuario eliminado correctamente", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error al eliminar usuario: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
