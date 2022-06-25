package com.usil.proyectomoviles.entity;

public class Actividad {
    private int id;
    private double monto;
    private String fecha;
    private String idUsuarioGasto;
    private String estado;
    private int idTipoActividad;

    public Actividad(){}

    public Actividad(int id, double monto, String fecha,String idUsuarioGasto, String estado, int idTipoActividad) {
        this.id = id;
        this.monto = monto;
        this.fecha=fecha;
        this.idUsuarioGasto=idUsuarioGasto;
        this.estado = estado;
        this.idTipoActividad = idTipoActividad;
    }

    public Actividad(double monto, String estado, int idTipoActividad) {
        this.monto = monto;
        this.estado = estado;
        this.idTipoActividad = idTipoActividad;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdUsuarioGasto() {
        return idUsuarioGasto;
    }

    public void setIdUsuarioGasto(String idUsuarioGasto) {
        this.idUsuarioGasto = idUsuarioGasto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }
}
