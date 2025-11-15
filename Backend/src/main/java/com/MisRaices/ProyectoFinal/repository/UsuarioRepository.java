package com.MisRaices.ProyectoFinal.repository;

import com.MisRaices.ProyectoFinal.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.correo=:correo AND u.password=:password")
    Optional<Usuario> findByCorreoAndPassword(String correo, String password);

    @Query("SELECT u FROM Usuario u WHERE u.correo=:correo")
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT u FROM Usuario u WHERE u.token=:token")
    Optional<Usuario> findByToken(String token);
}
