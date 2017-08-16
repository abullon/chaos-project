package com.fiveadvantages.chaos.datalayer.dto;

/**
 * @author Foncho A.K.A. Papi polvora
 */

public class CuponDTO {


    private int idCupon;

    private String descripcion;

    private float descuento;

    public CuponDTO() {

    }

    public CuponDTO(int idCupon, String descripcion, float descuento) {
        this.setIdCupon(idCupon);
        this.setDescripcion(descripcion);
        this.setDescuento(descuento);
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
