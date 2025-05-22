package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.EmailService;
import com.MisRaices.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String, Object> response;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmailService emailService;

    @Operation(
            summary = "Iniciar sesión",
            description = "Permite a un usuario autenticarse con correo y contraseña."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "500", description = "Credenciales incorrectas o error interno")
    })
    @PostMapping("/login")
    public ResponseEntity<?> IniciarSesion(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            usuario = usuarioService.findByCorreoAndPassword(usuario.getCorreo(), usuario.getPassword()).orElse(null);
            if (usuario == null) {
                response.put("error", "credenciales incorrectos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.put("data", usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario y envía un correo electrónico con el código de activación."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error al registrar usuario")
    })
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            String activationCode = generateActivationCode();

            usuario.setCodigo(activationCode);
            usuario.setActivo(false);

            emailService.sendActivationEmail(usuario.getCorreo(), activationCode);

            response.put("message", "Usuario registrado. Revisa tu correo para activar la cuenta.");
            return new ResponseEntity<>(usuarioService.guardar(usuario), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Activar cuenta",
            description = "Activa la cuenta de un usuario con el código enviado al correo electrónico."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Cuenta ya activada o código incorrecto")
    })
    @PostMapping("/activarCuenta")
    public ResponseEntity<?> activarCuenta(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            Usuario existingUser = usuarioService.findByCorreo(usuario.getCorreo()).orElse(null);
            if (existingUser == null) {
                response.put("error", "Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            if (existingUser.isActivo()) {
                response.put("error", "La cuenta ya está activada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (existingUser.getCodigo().equals(usuario.getCodigo())) {
                existingUser.setActivo(true);
                existingUser.setCodigo(null);
                return new ResponseEntity<>(usuarioService.guardar(existingUser), HttpStatus.OK);
            } else {
                response.put("error", "Código de activación incorrecto");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Solicitar token para restablecer contraseña",
            description = "Envía un token de restablecimiento de contraseña al correo electrónico del usuario."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token generado y enviado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Correo no asociado a un usuario"),
        @ApiResponse(responseCode = "500", description = "Error al enviar token")
    })
    @PostMapping("/solicitarToken")
    public ResponseEntity<?> solicitarRestablecerContraseña(@RequestBody Usuario usuario) {
        response = new HashMap<>();
        try {
            Usuario existeUser = usuarioService.findByCorreo(usuario.getCorreo()).orElse(null);
            if (existeUser == null) {
                response.put("error", "Usuario no encontrado con ese correo");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            String resetToken = generateActivationCode();

            existeUser.setToken(resetToken);
            existeUser.setTokenLimite(LocalDateTime.now().plusHours(1));
            emailService.sendResetPasswordEmail(existeUser.getCorreo(), resetToken);

            return new ResponseEntity<>(usuarioService.guardar(existeUser), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "no se pudo mandar token" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Restablecer contraseña",
            description = "Permite cambiar la contraseña si el token es válido y no ha expirado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contraseña restablecida exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token inválido o expirado"),
        @ApiResponse(responseCode = "500", description = "Error al restablecer contraseña")
    })
    @PostMapping("/restablecerPassword")
    public ResponseEntity<?> restablecerContraseña(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            Usuario existeUser = usuarioService.findByToken(usuario.getToken()).orElse(null);
            if (existeUser == null || existeUser.getTokenLimite().isBefore(LocalDateTime.now())) {
                response.put("error", "El token es inválido o ha expirado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            existeUser.setPassword(usuario.getPassword());
            existeUser.setToken(null);
            existeUser.setTokenLimite(null);

            return new ResponseEntity<>(usuarioService.guardar(existeUser), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para generar un código de activación aleatorio
    private String generateActivationCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();

        // Generar tres letras
        StringBuilder activationCode = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            activationCode.append(letters.charAt(random.nextInt(letters.length())));
        }

        // Generar tres números
        for (int i = 0; i < 3; i++) {
            activationCode.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return activationCode.toString();
    }
}
