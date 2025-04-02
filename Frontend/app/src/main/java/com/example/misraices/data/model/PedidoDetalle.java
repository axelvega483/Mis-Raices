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
