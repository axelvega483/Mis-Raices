package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioActivacionDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioGetDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioLoginDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioMapper;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioRegistroDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioRestablecerPasswordDTO;
import com.MisRaices.demo.DTOS.UsuarioDTO.UsuarioSolicitudTokenDTO;
import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.EmailService;
import com.MisRaices.demo.service.UsuarioService;
import com.MisRaices.demo.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("autenticacion")
public class CuentaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con correo y contraseña.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiRespo<UsuarioGetDTO>> iniciarSesion(@Valid @RequestBody UsuarioLoginDTO loginDTO) {
        try {
            Usuario user = usuarioService.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword()).orElse(null);
            if (user == null) {
                return new ResponseEntity<>(new ApiRespo<>("Credenciales incorrectas", null, false), HttpStatus.UNAUTHORIZED);
            }
            UsuarioGetDTO dto = UsuarioMapper.toDTO(user);
            return ResponseEntity.ok(new ApiRespo<>("Inicio de sesión exitoso", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario y envía un correo electrónico con el código de activación.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error al registrar usuario")
    })
    @PostMapping("/registro")
    public ResponseEntity<ApiRespo<?>> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO userRegistro) {
        try {
            String activationCode = generateActivationCode();
            Usuario usuario = new Usuario();
            usuario.setActivo(false);
            usuario.setCodigo(activationCode);
            usuario.setApellido(userRegistro.getApellido());
            usuario.setCorreo(userRegistro.getCorreo());
            usuario.setId(userRegistro.getId());
            usuario.setNombre(userRegistro.getNombre());
            usuario.setPassword(userRegistro.getPassword());
            usuario.setTelefono(userRegistro.getTelefono());

            emailService.sendActivationEmail(usuario.getCorreo(), activationCode);

            UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(usuario));
            return ResponseEntity.ok(new ApiRespo<>("Usuario registrado. Revisa tu correo para activar la cuenta.", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error al registrar usuario: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Activar cuenta", description = "Activa la cuenta de un usuario con el código enviado al correo electrónico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Cuenta ya activada o código incorrecto")
    })
    @PostMapping("/activarCuenta")
    public ResponseEntity<ApiRespo<?>> activarCuenta(@Valid @RequestBody UsuarioActivacionDTO activacionDTO) {
        try {
            Usuario existingUser = usuarioService.findByCorreo(activacionDTO.getCorreo()).orElse(null);
            if (existingUser == null) {
                return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
            if (existingUser.isActivo()) {
                return new ResponseEntity<>(new ApiRespo<>("La cuenta ya está activada", null, false), HttpStatus.BAD_REQUEST);
            }

            if (existingUser.getCodigo().equals(activacionDTO.getCodigo())) {
                existingUser.setActivo(true);
                existingUser.setCodigo(null);
                Usuario updatedUser = usuarioService.guardar(existingUser);
                UsuarioGetDTO dto = UsuarioMapper.toDTO(updatedUser);
                return ResponseEntity.ok(new ApiRespo<>("Cuenta activada exitosamente", dto, true));
            } else {
                return new ResponseEntity<>(new ApiRespo<>("Código de activación incorrecto", null, false), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Solicitar token para restablecer contraseña", description = "Envía un token de restablecimiento de contraseña al correo electrónico del usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token generado y enviado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Correo no asociado a un usuario"),
        @ApiResponse(responseCode = "500", description = "Error al enviar token")
    })
    @PostMapping("/solicitarToken")
    public ResponseEntity<ApiRespo<?>> solicitarRestablecerContraseña(@Valid @RequestBody UsuarioSolicitudTokenDTO solicitudTokenDTO) {
        try {
            Usuario existeUser = usuarioService.findByCorreo(solicitudTokenDTO.getCorreo()).orElse(null);
            if (existeUser == null) {
                return new ResponseEntity<>(new ApiRespo<>("Usuario no encontrado con ese correo", null, false), HttpStatus.NOT_FOUND);
            }

            String resetToken = generateActivationCode();
            existeUser.setToken(resetToken);
            existeUser.setTokenLimite(LocalDateTime.now().plusHours(1));
            emailService.sendResetPasswordEmail(existeUser.getCorreo(), resetToken);

            UsuarioGetDTO dto = UsuarioMapper.toDTO(usuarioService.guardar(existeUser));
            return ResponseEntity.ok(new ApiRespo<>("Token generado y enviado", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("No se pudo mandar token: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Restablecer contraseña", description = "Permite cambiar la contraseña si el token es válido y no ha expirado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contraseña restablecida exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token inválido o expirado"),
        @ApiResponse(responseCode = "500", description = "Error al restablecer contraseña")
    })
    @PostMapping("/restablecerPassword")
    public ResponseEntity<ApiRespo<?>> restablecerContraseña(@Valid @RequestBody UsuarioRestablecerPasswordDTO restablecerDTO) {
        try {
            Usuario existeUser = usuarioService.findByToken(restablecerDTO.getToken()).orElse(null);
            if (existeUser == null || existeUser.getTokenLimite().isBefore(LocalDateTime.now())) {
                return new ResponseEntity<>(new ApiRespo<>("El token es inválido o ha expirado", null, false), HttpStatus.BAD_REQUEST);
            }

            existeUser.setPassword(restablecerDTO.getPassword());
            existeUser.setToken(null);
            existeUser.setTokenLimite(null);

            Usuario savedUser = usuarioService.guardar(existeUser);
            UsuarioGetDTO dto = UsuarioMapper.toDTO(savedUser);
            return ResponseEntity.ok(new ApiRespo<>("Contraseña restablecida exitosamente", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateActivationCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();

        StringBuilder activationCode = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            activationCode.append(letters.charAt(random.nextInt(letters.length())));
        }
        for (int i = 0; i < 3; i++) {
            activationCode.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return activationCode.toString();
    }
}
