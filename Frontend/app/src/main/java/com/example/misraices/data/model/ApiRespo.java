package com.example.misraices.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiRespo<T> {

    private String mensaje;
    private T data;
    private boolean exito;

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
}
