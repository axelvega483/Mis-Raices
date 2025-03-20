package com.MisRaices.demo.interfaz;

import java.util.List;
import java.util.Optional;
import com.MisRaices.demo.entity.Usuario;

public interface UsuarioInterfaz {

    public Usuario guardar(Usuario usuario);

    public void eliminar(Integer id);

    public Optional<Usuario> obtener(Integer id);

    public List<Usuario> listar();
}
