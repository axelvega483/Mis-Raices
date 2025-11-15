package com.MisRaices.ProyectoFinal.repository;

import com.MisRaices.ProyectoFinal.entity.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query("SELECT c FROM Categoria c WHERE c.nombre =:nombre")
    Optional<Categoria> findByNombre(String nombre);

}
