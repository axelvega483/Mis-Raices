package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Categoria;
import com.MisRaices.demo.entity.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Override
    public Optional<Producto> findById(Integer id);
    
     List<Producto> findByCategoria(Categoria categoria);
     
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    public List<Producto> findByNombre(String nombre);
}
