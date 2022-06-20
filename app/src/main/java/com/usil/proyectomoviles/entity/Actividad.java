package com.usil.proyectomoviles.entity;

public class Actividad {
    private int id;
    private double monto;
    private String solicitudEstado;
    private int idTipoActividad;

    public Actividad(){}

    public Actividad(int id, double monto, String solicitudEstado, int idTipoActividad) {
        this.id = id;
        this.monto = monto;
        this.solicitudEstado = solicitudEstado;
        this.idTipoActividad = idTipoActividad;
    }

    public Actividad(double monto, String solicitudEstado, int idTipoActividad) {
        this.monto = monto;
        this.solicitudEstado = solicitudEstado;
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

    public String getSolicitudEstado() {
        return solicitudEstado;
    }

    public void setSolicitudEstado(String solicitudEstado) {
        this.solicitudEstado = solicitudEstado;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }
}
