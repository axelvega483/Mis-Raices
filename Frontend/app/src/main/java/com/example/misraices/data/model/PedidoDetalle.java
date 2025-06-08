package com.example.misraices.data.model;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalle implements Serializable {
    private Integer id;
    private Integer cantidad;
    private Producto producto;
    private Pedido pedido;

    public PedidoDetalle( Producto producto,String nombre, Double precio, Integer stock) {
        this.producto = producto;
        this.producto.setNombre(nombre);
        this.producto.setPrecio(precio);
        this.producto.setStock(stock);
    }
    public double getSubtotal() {
        if (producto == null || cantidad == null) return 0.0;
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "PedidoDetalle{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", producto=" + producto +
                ", pedido=" + pedido +
                '}';
    }
}
