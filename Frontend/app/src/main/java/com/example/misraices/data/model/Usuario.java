package com.example.misraices.data.model;

import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    private Integer id;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String direccion;
    private String correo;
    private String password;
    private boolean activo;
    private String codigo;
    private String token;
    private List<Pedido> pedidos;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono=" + telefono +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", activo=" + activo +
                ", codigo='" + codigo + '\'' +
                ", token='" + token + '\'' +
                ", direccion='" + direccion + '\''+
                ", pedidos=" + pedidos +
                '}';
    }
}
