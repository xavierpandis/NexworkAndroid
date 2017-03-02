package com.cjx.nexwork.model;

import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by jotabono on 8/2/17.
 */

public class Work {

    Long id;

    String cargo;

    Date fechaInicio;

    Date fechaFin;

    Boolean actualmente;

    String descripcionCargo;

    //Empresa empresa;

    //User trabajador;


    public Work() {
    }

    public Work(Long id, String cargo, Date fechaInicio, Date fechaFin, Boolean actualmente, String descripcionCargo) {
        this.id = id;
        this.cargo = cargo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.actualmente = actualmente;
        this.descripcionCargo = descripcionCargo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActualmente() {
        return actualmente;
    }

    public void setActualmente(Boolean actualmente) {
        this.actualmente = actualmente;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Work work = (Work) o;

        if (id != null ? !id.equals(work.id) : work.id != null) return false;
        if (cargo != null ? !cargo.equals(work.cargo) : work.cargo != null) return false;
        if (fechaInicio != null ? !fechaInicio.equals(work.fechaInicio) : work.fechaInicio != null)
            return false;
        if (fechaFin != null ? !fechaFin.equals(work.fechaFin) : work.fechaFin != null)
            return false;
        if (actualmente != null ? !actualmente.equals(work.actualmente) : work.actualmente != null)
            return false;
        return descripcionCargo != null ? descripcionCargo.equals(work.descripcionCargo) : work.descripcionCargo == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cargo != null ? cargo.hashCode() : 0);
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (actualmente != null ? actualmente.hashCode() : 0);
        result = 31 * result + (descripcionCargo != null ? descripcionCargo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", cargo='" + cargo + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", actualmente=" + actualmente +
                ", descripcionCargo='" + descripcionCargo + '\'' +
                '}';
    }
}
