/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.MisRaices.demo.interfaz;

import java.util.List;
import java.util.Optional;
import com.MisRaices.demo.entity.Usuario;

/**
 *
 * @author Sofia
 */
public interface UsuarioInterfaz {
     public Usuario guardar(Usuario usuario);

    public void eliminar(Integer id);

    public Optional<Usuario> obtener(Integer id);

    public List<Usuario> listar();
}
