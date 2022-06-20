package com.usil.proyectomoviles.entity;

public class Grupo {
    private int id;
    private String nombreGrupo;

    public Grupo(){}

    public Grupo(int id, String nombreGrupo) {
        this.id = id;
        this.nombreGrupo = nombreGrupo;
    }

    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
