package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.Pedido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Override
    public Optional<Pedido> findById(Integer id);
}
