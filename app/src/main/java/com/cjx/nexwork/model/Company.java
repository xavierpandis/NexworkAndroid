package com.cjx.nexwork.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xavi on 29/03/2017.
 */

public class Company implements Serializable {

    private Long id;

    private String nombre;

    private Integer numEmpleados;

    private String ubicacion;

    private String latitud;

    private String longitud;

    private Date fechaFundacion;

    public Company() {
    }

    public Company(Long id, String nombre, Integer numEmpleados, String ubicacion, String latitud, String longitud, Date fechaFundacion) {
        this.id = id;
        this.nombre = nombre;
        this.numEmpleados = numEmpleados;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaFundacion = fechaFundacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(Integer numEmpleados) {
        this.numEmpleados = numEmpleados;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;
        if (nombre != null ? !nombre.equals(company.nombre) : company.nombre != null) return false;
        if (numEmpleados != null ? !numEmpleados.equals(company.numEmpleados) : company.numEmpleados != null)
            return false;
        if (ubicacion != null ? !ubicacion.equals(company.ubicacion) : company.ubicacion != null)
            return false;
        if (latitud != null ? !latitud.equals(company.latitud) : company.latitud != null)
            return false;
        if (longitud != null ? !longitud.equals(company.longitud) : company.longitud != null)
            return false;
        return fechaFundacion != null ? fechaFundacion.equals(company.fechaFundacion) : company.fechaFundacion == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (numEmpleados != null ? numEmpleados.hashCode() : 0);
        result = 31 * result + (ubicacion != null ? ubicacion.hashCode() : 0);
        result = 31 * result + (latitud != null ? latitud.hashCode() : 0);
        result = 31 * result + (longitud != null ? longitud.hashCode() : 0);
        result = 31 * result + (fechaFundacion != null ? fechaFundacion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numEmpleados=" + numEmpleados +
                ", ubicacion='" + ubicacion + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", fechaFundacion=" + fechaFundacion +
                '}';
    }
}
