package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.interfaz.UsuarioInterfaz;
import com.MisRaices.demo.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        Usuario usuario = obtener(id).get();
        usuario.setActivo(false);
        repo.save(usuario);
    }

    @Override
    public Optional<Usuario> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Optional<Usuario> findByCorreoAndPassword(String correo, String password) {
        return repo.findByCorreoAndPassword(correo, password);
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    public Optional<Usuario> findByToken(String token) {
        return repo.findByToken(token);
    }
}
