package com.usil.proyectomoviles.entity;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre,apellidos,correo, usuario, contrasena;

    public Usuario() {

    }

    public Usuario(String nombre, String apellidos, String correo, String usuario, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContraseña(String contrasena) {
        this.contrasena = contrasena;
    }
}