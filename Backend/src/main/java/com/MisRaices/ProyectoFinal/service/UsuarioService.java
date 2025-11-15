package com.MisRaices.ProyectoFinal.service;

import com.MisRaices.ProyectoFinal.DTOS.UsuarioDTO.*;
import com.MisRaices.ProyectoFinal.entity.Direccion;
import com.MisRaices.ProyectoFinal.entity.Usuario;
import com.MisRaices.ProyectoFinal.interfaz.UsuarioInterfaz;
import com.MisRaices.ProyectoFinal.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private UsuarioMapper mapper;
    @Autowired
    private EmailService emailService;

    @Override
    public UsuarioGetDTO crear(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = mapper.toEntity(registroDTO);
        Usuario usuarioGuardado = repo.save(usuario);
        emailService.sendActivationEmail(usuarioGuardado.getCorreo(), usuarioGuardado.getCodigo());
        return mapper.toDTO(usuarioGuardado);
    }


    @Override
    public Optional<UsuarioGetDTO> obtener(Integer id) {
        return repo.findById(id).filter(Usuario::isActivo).map(mapper::toDTO);
    }

    @Override
    public UsuarioGetDTO actualizar(Integer id, UsuarioPutDTO putDTO) {
        Usuario usuario = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        mapper.updateEntity(usuario, putDTO);
        Usuario usuarioActualizado = repo.save(usuario);
        return mapper.toDTO(usuarioActualizado);
    }

    @Override
    public UsuarioGetDTO cargarDireccion(Integer usuarioId, Direccion direccion) {
        Usuario usuario = repo.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setDireccion(direccion);
        Usuario usuarioActualizado = repo.save(usuario);
        return mapper.toDTO(usuarioActualizado);
    }

    @Override
    public Optional<UsuarioGetDTO> findByCorreoAndPassword(String correo, String password) {
        return repo.findByCorreoAndPassword(correo, password).map(usuario -> mapper.toDTO(usuario));
    }

    @Override
    public Optional<UsuarioGetDTO> findByCorreoAndCodigo(String correo, String codigo) {
        Optional<Usuario> usuarioOpt = repo.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.isActivo()) {
            return Optional.empty();
        }

        if (!usuario.getCodigo().equals(codigo)) {
            return Optional.empty();
        }

        usuario.setActivo(true);
        usuario.setCodigo(null);
        Usuario usuarioActualizado = repo.save(usuario);
        return Optional.of(mapper.toDTO(usuarioActualizado));
    }

    @Override
    public Optional<UsuarioGetDTO> findByCorreo(String correo) {
        return repo.findByCorreo(correo).map(usuario -> {
            if (usuario.isActivo()) {
                throw new RuntimeException("La cuenta ya está activada");
            }
            String resetToken = mapper.generateActivationCode();
            usuario.setToken(resetToken);
            usuario.setTokenLimite(LocalDateTime.now().plusHours(1));
            emailService.sendResetPasswordEmail(usuario.getCorreo(), resetToken);
            Usuario usuarioGuardado = repo.save(usuario);
            return mapper.toDTO(usuarioGuardado);
        });

    }

    @Override
    public Optional<UsuarioGetDTO> findByToken(UsuarioRestablecerPasswordDTO restablecerDTO) {
        return repo.findByToken(restablecerDTO.getToken()).map(usuario -> {
            if (usuario.getTokenLimite().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("El token es inválido o ha expirado");
            }
            usuario.setPassword(restablecerDTO.getPassword());
            usuario.setToken(null);
            usuario.setTokenLimite(null);
            usuario.setCodigo(null);
            usuario.setActivo(true);

            Usuario savedUser = repo.save(usuario);
            return mapper.toDTO(savedUser);
        });
    }
}
