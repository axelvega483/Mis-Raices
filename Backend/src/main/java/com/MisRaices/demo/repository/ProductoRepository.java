package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Producto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Override
    public Optional<Producto> findById(Integer id);
}
