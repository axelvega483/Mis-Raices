package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.TarjetaCredito;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Integer> {

    @Override
    public Optional<TarjetaCredito> findById(Integer id);
}
