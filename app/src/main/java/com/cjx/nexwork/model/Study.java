package com.cjx.nexwork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xavi on 08/02/2017.
 */

public class Study implements Serializable{

    Long id;
    Date fechaInicio;
    Date fechaFinal;
    Boolean actualmente;
    String curso;
    Float nota;
    String descripcion;

    @SerializedName("centro")
    Center center;

    public Study() {
    }

    public Study(Long id, Date fechaInicio, Date fechaFinal, Boolean actualmente, String curso, Float nota, String descripcion, Center center) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.actualmente = actualmente;
        this.curso = curso;
        this.nota = nota;
        this.descripcion = descripcion;
        this.center = center;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Boolean getActualmente() {
        return actualmente;
    }

    public void setActualmente(Boolean actualmente) {
        this.actualmente = actualmente;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "Study{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                ", actualmente=" + actualmente +
                ", curso='" + curso + '\'' +
                ", nota=" + nota +
                ", descripcion='" + descripcion + '\'' +
                ", center=" + center +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Study study = (Study) o;

        if (id != null ? !id.equals(study.id) : study.id != null) return false;
        if (fechaInicio != null ? !fechaInicio.equals(study.fechaInicio) : study.fechaInicio != null)
            return false;
        if (fechaFinal != null ? !fechaFinal.equals(study.fechaFinal) : study.fechaFinal != null)
            return false;
        if (actualmente != null ? !actualmente.equals(study.actualmente) : study.actualmente != null)
            return false;
        if (curso != null ? !curso.equals(study.curso) : study.curso != null) return false;
        if (nota != null ? !nota.equals(study.nota) : study.nota != null) return false;
        if (descripcion != null ? !descripcion.equals(study.descripcion) : study.descripcion != null)
            return false;
        return center != null ? center.equals(study.center) : study.center == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (fechaFinal != null ? fechaFinal.hashCode() : 0);
        result = 31 * result + (actualmente != null ? actualmente.hashCode() : 0);
        result = 31 * result + (curso != null ? curso.hashCode() : 0);
        result = 31 * result + (nota != null ? nota.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (center != null ? center.hashCode() : 0);
        return result;
    }
}
