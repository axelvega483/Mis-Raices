package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioPutDTO;
import com.MisRaices.ProyectoFinal.entity.Direccion;
import com.MisRaices.ProyectoFinal.interfaz.UsuarioInterfaz;
import com.MisRaices.ProyectoFinal.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("usuario")
@Tag(name = "Usuario", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz usuarioService;

    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve un usuario específico según su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario encontrado"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> obtenerUsuario(@Parameter(description = "ID del usuario a obtener", example = "1", required = true) @PathVariable Integer id) {
        return usuarioService.obtener(id).map(usuario -> new ResponseEntity<>(new ApiRespo<>("Usuario encontrado", usuario, true), HttpStatus.OK)).orElse(new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Modificar un usuario", description = "Actualiza los datos de un usuario existente mediante su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> modificarUsuario(@Parameter(description = "ID del usuario a modificar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody UsuarioPutDTO usuarioPut) {
        UsuarioGetDTO dto = usuarioService.actualizar(id, usuarioPut);
        return new ResponseEntity<>(new ApiRespo<>("Usuario actualizado correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Asignar o actualizar dirección del usuario", description = "Permite asignar o actualizar la dirección embebida de un usuario existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"), @ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @ApiResponse(responseCode = "400", description = "Datos de dirección inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/direccion/{id}")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> cargarDireccion(@Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos de la dirección a asignar", required = true) @Valid @RequestBody Direccion direccion) {
        UsuarioGetDTO dto = usuarioService.cargarDireccion(id, direccion);
        return new ResponseEntity<>(new ApiRespo<>("Dirección actualizada correctamente", dto, true), HttpStatus.OK);
    }
}
