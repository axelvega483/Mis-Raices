package com.example.misraices.data.model;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCredito implements Serializable {
    private Integer id;

    private String titular;
    private String numero;
    private String fechaVencimiento;
    private String codigoSeguridad;
    private String tipo;
    private Double saldo;
    private Usuario usuario;

    @Override
    public String toString() {
        return "TarjetaCredito{" +
                "id=" + id +
                ", titular='" + titular + '\'' +
                ", numero='" + numero + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", codigoSeguridad='" + codigoSeguridad + '\'' +
                ", tipo='" + tipo + '\'' +
                ", saldo=" + saldo +
                ", usuario=" + usuario +
                '}';
    }
}
