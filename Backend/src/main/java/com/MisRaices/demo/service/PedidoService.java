package com.MisRaices.demo.service;

import com.MisRaices.demo.entity.Pedido;
import com.MisRaices.demo.entity.PedidoDetalle;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.interfaz.PedidoInterfaz;
import com.MisRaices.demo.repository.PedidoRepository;
import com.MisRaices.demo.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService implements PedidoInterfaz {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ProductoRepository productoRepo;


    @Override
    public Pedido guardar(Pedido pedido) {
        if (pedido.getDetalle() != null && !pedido.getDetalle().isEmpty()) {
            for (PedidoDetalle detalle : pedido.getDetalle()) {
                Producto producto = detalle.getProducto();
                // Verificar que el producto exista en la base de datos
                producto = productoRepo.findById(producto.getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setProducto(producto); // Asignar el producto
                detalle.setPedido(pedido); // Asociar el detalle al pedido
            }
        }
        return repo.save(pedido); // Guardar el pedido con los detalles
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Pedido> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Pedido> listar() {
        return repo.findAll();
    }

}
