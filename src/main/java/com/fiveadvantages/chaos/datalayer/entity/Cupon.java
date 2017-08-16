package com.fiveadvantages.chaos.datalayer.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "chaos_cupon")
public class Cupon {

    @DatabaseField(canBeNull = false)
    private int idCupon;
    @DatabaseField(canBeNull = false)
    private String descripcion;
    @DatabaseField(canBeNull = false)
    private float descuento;

    public Cupon() {

    }

    public Cupon(int idCupon, String descripcion, float descuento) {
        this.idCupon = idCupon;
        this.descripcion = descripcion;
        this.descuento = descuento;
    }

    public int getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(int idCupon) {
        this.idCupon = idCupon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
}
