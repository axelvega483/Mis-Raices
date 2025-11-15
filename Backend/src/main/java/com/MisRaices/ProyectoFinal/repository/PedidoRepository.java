package com.MisRaices.ProyectoFinal.repository;

import com.MisRaices.ProyectoFinal.entity.Pedido;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Override
    Optional<Pedido> findById(Integer id);
}
