package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.EmailService;
import com.MisRaices.demo.service.UsuarioService;
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

    @PostMapping("/login")
     public ResponseEntity<?> IniciarSesion(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            usuario = usuarioService.findByCorreoAndPassword(usuario.getCorreo(), usuario.getPassword()).orElse(null);
            if (usuario == null) {
                response.put("error", "credenciales incorrectos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            response = new HashMap<>();
            String activationCode = generateActivationCode();
            usuario.setCodigo(activationCode);
            usuario.setActivo(false);
            usuarioService.guardar(usuario);

            emailService.sendActivationEmail(usuario.getCorreo(), activationCode);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
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
    public ResponseEntity<?> activarCuenta(@RequestBody Usuario usuario) {
        try {
            response= new HashMap<>();
            Usuario existingUser = usuarioService.findByCorreo(usuario.getCorreo()).orElse(null);
            if (existingUser == null) {
                response.put("error","Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            if (existingUser.isActivo()) {
                response.put("data","La cuenta ya está activada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (existingUser.getCodigo().equals(usuario.getCodigo())) {
                existingUser.setActivo(true);
                usuarioService.guardar(existingUser);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            } else {
               response.put("error","Código de activación incorrecto");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/solicitarToken")
    public ResponseEntity<?> solicitarRestablecerContraseña(@RequestParam String correo) {
        try {
            response= new HashMap<>();
            Usuario usuario = usuarioService.findByCorreo(correo).orElse(null);
            if (usuario == null) {
               response.put("error", "Correo no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            String resetToken = generateActivationCode();

            usuario.setToken(resetToken);
            usuario.setTokenLimite(LocalDateTime.now().plusHours(1));
            usuarioService.guardar(usuario);

            emailService.sendResetPasswordEmail(usuario.getCorreo(), resetToken);

            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/restablecerContraseña")
    public ResponseEntity<?> restablecerContraseña(@RequestParam String token, @RequestParam String password
    ) {
        try {
            response= new HashMap<>();
            Usuario usuario = usuarioService.findByToken(token).orElse(null);
            if (usuario == null || usuario.getTokenLimite().isBefore(LocalDateTime.now())) {
                response.put("erro", "token expirado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            usuario.setPassword(password);
            usuario.setToken(null);
            usuario.setTokenLimite(null);
            usuarioService.guardar(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
