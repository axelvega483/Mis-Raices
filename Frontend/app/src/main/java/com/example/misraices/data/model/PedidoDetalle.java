package com.example.misraices.data.model;

import java.io.Serializable;

public class PedidoDetalle implements Serializable {
    private Integer id;
    private Integer cantidad;
    private Producto producto;
    private Pedido pedido;

    public PedidoDetalle() {
    }

    public PedidoDetalle(Integer cantidad, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
