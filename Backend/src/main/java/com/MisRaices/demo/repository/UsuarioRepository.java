/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sofia
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Override
    public Optional<Usuario> findById(Integer id);

}
