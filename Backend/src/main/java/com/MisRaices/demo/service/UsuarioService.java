/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.interfaz.UsuarioInterfaz;
import com.MisRaices.demo.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sofia
 */
@Service
@Transactional
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

}
