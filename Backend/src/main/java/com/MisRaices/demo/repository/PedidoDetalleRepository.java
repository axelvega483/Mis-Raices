
package com.MisRaices.demo.repository;

import com.MisRaices.demo.entity.PedidoDetalle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {
    @Override
    public Optional<PedidoDetalle> findById(Integer id);
}
