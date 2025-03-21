package com.MisRaices.demo.interfaz;

import com.MisRaices.demo.entity.Pedido;
import java.util.List;
import java.util.Optional;


public interface PedidoInterfaz {
    public Pedido guardar(Pedido pedido);

    public void eliminar(Integer id);

    public Optional<Pedido> obtener(Integer id);

    public List<Pedido> listar();
}
