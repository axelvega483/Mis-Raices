package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioActivacionDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioLoginDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioRegistroDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioRestablecerPasswordDTO;
import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.UsuarioSolicitudTokenDTO;
import com.MisRaices.ProyectoFinal.interfaz.UsuarioInterfaz;
import com.MisRaices.ProyectoFinal.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("autenticacion")
@Tag(name = "Autenticación y Gestión de Cuentas", description = "Operaciones relacionadas con la autenticación y gestión de cuentas de usuario")
public class CuentaController {

    @Autowired
    private UsuarioInterfaz usuarioService;

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con correo y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> iniciarSesion(
            @Parameter(description = "Credenciales de acceso", required = true)
            @Valid @RequestBody UsuarioLoginDTO loginDTO) {
        Optional<UsuarioGetDTO> usuario = usuarioService.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword());
        if (usuario.isPresent()) {
            return new ResponseEntity<>(new ApiRespo<>("Inicio de sesión exitoso", usuario.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Credenciales incorrectas", null, false), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario y envía un correo electrónico con el código de activación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/registro")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> registrarUsuario(
            @Parameter(description = "Datos del usuario a registrar", required = true)
            @Valid @RequestBody UsuarioRegistroDTO userRegistro) {
        UsuarioGetDTO dto = usuarioService.crear(userRegistro);
        return new ResponseEntity<>(new ApiRespo<>("Usuario registrado. Revisa tu correo para activar la cuenta.", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Activar cuenta", description = "Activa la cuenta de un usuario con el código enviado al correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Cuenta ya activada o código incorrecto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/activar-cuenta")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> activarCuenta(
            @Parameter(description = "Datos de activación de la cuenta", required = true)
            @Valid @RequestBody UsuarioActivacionDTO activacionDTO) {
        Optional<UsuarioGetDTO> usuario = usuarioService.findByCorreoAndCodigo(activacionDTO.getCorreo(), activacionDTO.getCodigo());
        if (usuario.isPresent()) {
            return new ResponseEntity<>(new ApiRespo<>("Cuenta activada exitosamente", usuario.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado o código incorrecto", null, false), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Solicitar token para restablecer contraseña", description = "Envía un token de restablecimiento de contraseña al correo electrónico del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generado y enviado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Correo no asociado a un usuario"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/solicitar-token")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> solicitarRestablecerContraseña(
            @Parameter(description = "Correo para solicitar restablecimiento", required = true)
            @Valid @RequestBody UsuarioSolicitudTokenDTO solicitudTokenDTO) {
        Optional<UsuarioGetDTO> usuario = usuarioService.findByCorreo(solicitudTokenDTO.getCorreo());
        if (usuario.isPresent()) {
            return new ResponseEntity<>(new ApiRespo<>("Token generado y enviado", usuario.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado con ese correo", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Restablecer contraseña", description = "Permite cambiar la contraseña si el token es válido y no ha expirado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña restablecida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido o expirado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/restablecer-password")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> restablecerContraseña(
            @Parameter(description = "Datos para restablecer contraseña", required = true)
            @Valid @RequestBody UsuarioRestablecerPasswordDTO restablecerDTO) {
        Optional<UsuarioGetDTO> usuario = usuarioService.findByToken(restablecerDTO);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(new ApiRespo<>("Contraseña restablecida exitosamente", usuario.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiRespo<>("Token inválido o expirado", null, false), HttpStatus.BAD_REQUEST);
        }
    }
}