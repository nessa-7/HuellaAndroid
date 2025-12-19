package com.nessa.huella.model;

import java.util.Date;

public class ActividadCarbono {

    private Integer id;
    private String tipo;
    private Integer cantidad;
    private Double emision;
    private Date fecha;

    //  Constructor vacío (útil para BD, setters, etc.)
    public ActividadCarbono() {
    }

    //  Constructor SIN id (para insertar)
    public ActividadCarbono(String tipo, Integer cantidad, Double emision, Date fecha) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.emision = emision;
        this.fecha = fecha;
    }

    //  Constructor CON id (para lecturas de BD)
    public ActividadCarbono(Integer id, String tipo, Integer cantidad, Double emision, Date fecha) {
        this.id = id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.emision = emision;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getEmision() {
        return emision;
    }

    public void setEmision(Double emision) {
        this.emision = emision;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
