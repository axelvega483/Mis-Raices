package com.example.misraices.data.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {
    private Integer id;
    private String direccion;
    private String fechaPedido;
    private String estado;
    private List<PedidoDetalle> detalle;
    private Double total;
    private Usuario usuario;

    public Pedido() {
    }

    public Pedido(String direccion, String fechaPedido, String estado, List<PedidoDetalle> detalle, Double total, Usuario usuario) {
        this.direccion = direccion;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.detalle = detalle;
        this.total = total;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<PedidoDetalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<PedidoDetalle> detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                ", detalle=" + detalle +
                ", total=" + total +
                ", usuario=" + usuario +
                '}';
    }
}
