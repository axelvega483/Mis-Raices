package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Override
    public Optional<Categoria> findById(Integer id);

}
