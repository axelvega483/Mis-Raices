package com.MisRaices.ProyectoFinal.repository;

import com.MisRaices.ProyectoFinal.entity.TarjetaCredito;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Integer> {

    @Override
    Optional<TarjetaCredito> findById(Integer id);
}
