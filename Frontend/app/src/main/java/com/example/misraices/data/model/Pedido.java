package com.example.misraices.data.model;

import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido implements Serializable {
    private Integer id;
    private String fechaPedido;
    private String estado;
    private List<PedidoDetalle> detalle;
    private Double total;
    private Usuario usuario;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                ", detalle=" + detalle +
                ", total=" + total +
                ", usuario=" + usuario +
                '}';
    }
}
