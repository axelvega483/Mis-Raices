package com.example.misraices.data.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private Integer id;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String correo;
    private String password;
    private boolean activo;
    private String codigo;//codigo de activacion de cuenta
    private String token;//token para restablecer contraceña
    private String imgUri;
    private List<Pedido> pedidos;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, Long telefono, String correo, String password, boolean activo, String codigo, String token, String imgUri, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
        this.activo = activo;
        this.codigo = codigo;
        this.token = token;
        this.imgUri = imgUri;
        this.pedidos = pedidos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

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
                ", imgUri='" + imgUri + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}
