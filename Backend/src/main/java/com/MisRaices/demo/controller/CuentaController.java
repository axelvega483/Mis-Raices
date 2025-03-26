package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.EmailService;
import com.MisRaices.demo.service.UsuarioService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("autenticacion")
public class CuentaController {

    private Map<String, Object> response;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> IniciarSesion(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            usuario = usuarioService.findByCorreoAndPassword(usuario.getCorreo(), usuario.getPassword()).orElse(null);
            if (usuario == null) {
                response.put("error", "credenciales incorrectos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.put("login exitos", usuario);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("usuario", usuario);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            String activationCode = generateActivationCode();

            usuario.setCodigo(activationCode);
            usuario.setActivo(false);
            usuarioService.guardar(usuario);

            emailService.sendActivationEmail(usuario.getCorreo(), activationCode);

            response.put("message", "Usuario registrado. Revisa tu correo para activar la cuenta.");
            return new ResponseEntity<>(response, HttpStatus.OK);
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

    @PostMapping("/activarCuenta")
    public ResponseEntity<Map<String, Object>> activarCuenta(@RequestBody Usuario usuario) {
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
                usuarioService.guardar(existingUser);
                response.put("message", "Cuenta activada exitosamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("error", "Código de activación incorrecto");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/solicitarToken")
    public ResponseEntity<Map<String, Object>> solicitarRestablecerContraseña(@RequestParam String correo
    ) {
        response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.findByCorreo(correo).orElse(null);
            if (usuario == null) {
                response.put("error", "Usuario no encontrado con ese correo");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            String resetToken = generateActivationCode();

            usuario.setToken(resetToken);
            usuario.setTokenLimite(LocalDateTime.now().plusHours(1));
            usuarioService.guardar(usuario);

            emailService.sendResetPasswordEmail(usuario.getCorreo(), resetToken);

            response.put("codigo para contraseña", "Se ha enviado un correo con el código de activación");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "no se pudo mandar token" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/restablecerContraseña")
    public ResponseEntity<Map<String, Object>> restablecerContraseña(@RequestParam String token, @RequestParam String password
    ) {
        try {
            response = new HashMap<>();
            Usuario usuario = usuarioService.findByToken(token).orElse(null);
            if (usuario == null || usuario.getTokenLimite().isBefore(LocalDateTime.now())) {
                response.put("error", "El token es inválido o ha expirado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            usuario.setPassword(password);
            usuario.setToken(null);
            usuario.setTokenLimite(null);
            usuarioService.guardar(usuario);

            response.put("message", "Contraseña restablecida exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
