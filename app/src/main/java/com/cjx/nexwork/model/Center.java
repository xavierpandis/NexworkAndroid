package com.cjx.nexwork.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xavi on 29/03/2017.
 */

public class Center implements Serializable {

    private Long id;

    private String nombre;

    private Integer numEmpleados;

    private Date fechaFundacion;

    private String ubicacion;

    private String latitud;

    private String longitud;

    public Center() {
    }

    public Center(Long id, String nombre, Integer numEmpleados, Date fechaFundacion, String ubicacion, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.numEmpleados = numEmpleados;
        this.fechaFundacion = fechaFundacion;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Center center = (Center) o;

        if (id != null ? !id.equals(center.id) : center.id != null) return false;
        if (nombre != null ? !nombre.equals(center.nombre) : center.nombre != null) return false;
        if (numEmpleados != null ? !numEmpleados.equals(center.numEmpleados) : center.numEmpleados != null)
            return false;
        if (fechaFundacion != null ? !fechaFundacion.equals(center.fechaFundacion) : center.fechaFundacion != null)
            return false;
        if (ubicacion != null ? !ubicacion.equals(center.ubicacion) : center.ubicacion != null)
            return false;
        if (latitud != null ? !latitud.equals(center.latitud) : center.latitud != null)
            return false;
        return longitud != null ? longitud.equals(center.longitud) : center.longitud == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (numEmpleados != null ? numEmpleados.hashCode() : 0);
        result = 31 * result + (fechaFundacion != null ? fechaFundacion.hashCode() : 0);
        result = 31 * result + (ubicacion != null ? ubicacion.hashCode() : 0);
        result = 31 * result + (latitud != null ? latitud.hashCode() : 0);
        result = 31 * result + (longitud != null ? longitud.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Center{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numEmpleados=" + numEmpleados +
                ", fechaFundacion=" + fechaFundacion +
                ", ubicacion='" + ubicacion + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
