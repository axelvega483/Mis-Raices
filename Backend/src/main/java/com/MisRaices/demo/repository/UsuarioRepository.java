package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Override
    public Optional<Usuario> findById(Integer id);

    @Query("SELECT u FROM Usuario u WHERE u.correo=:correo AND u.password=:password AND u.activo=true")
    Optional<Usuario> findByCorreoAndPassword(String correo, String password);

    @Query("SELECT u FROM Usuario u WHERE u.correo=:correo")
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT u FROM Usuario u WHERE u.token=:token")
    public Optional<Usuario> findByToken(String token);
}
