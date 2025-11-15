package com.MisRaices.ProyectoFinal.repository;

import com.MisRaices.ProyectoFinal.entity.PedidoDetalle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {
    @Override
    Optional<PedidoDetalle> findById(Integer id);
}
