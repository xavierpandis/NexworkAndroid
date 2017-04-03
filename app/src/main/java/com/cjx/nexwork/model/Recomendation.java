package com.cjx.nexwork.model;

import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalDateTime;

import java.io.Serializable;

/**
 * Created by Xavi on 29/03/2017.
 */

public class Recomendation implements Serializable {

    private Long id;

    private String texto;

    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaResolucion;

    private Boolean aceptada;

    private User recomendador;

    private User recomendado;

    @SerializedName("trabajo")
    private Work work;

    @SerializedName("empresa")
    private Company company;

    public Recomendation() {
    }

    public Recomendation(Long id, String texto, LocalDateTime fechaEnvio, LocalDateTime fechaResolucion, Boolean aceptada, User recomendador, User recomendado, Work work, Company company) {
        this.id = id;
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
        this.fechaResolucion = fechaResolucion;
        this.aceptada = aceptada;
        this.recomendador = recomendador;
        this.recomendado = recomendado;
        this.work = work;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Boolean getAceptada() {
        return aceptada;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public User getRecomendador() {
        return recomendador;
    }

    public void setRecomendador(User recomendador) {
        this.recomendador = recomendador;
    }

    public User getRecomendado() {
        return recomendado;
    }

    public void setRecomendado(User recomendado) {
        this.recomendado = recomendado;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recomendation that = (Recomendation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (texto != null ? !texto.equals(that.texto) : that.texto != null) return false;
        if (fechaEnvio != null ? !fechaEnvio.equals(that.fechaEnvio) : that.fechaEnvio != null)
            return false;
        if (fechaResolucion != null ? !fechaResolucion.equals(that.fechaResolucion) : that.fechaResolucion != null)
            return false;
        if (aceptada != null ? !aceptada.equals(that.aceptada) : that.aceptada != null)
            return false;
        if (recomendador != null ? !recomendador.equals(that.recomendador) : that.recomendador != null)
            return false;
        if (recomendado != null ? !recomendado.equals(that.recomendado) : that.recomendado != null)
            return false;
        if (work != null ? !work.equals(that.work) : that.work != null) return false;
        return company != null ? company.equals(that.company) : that.company == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (texto != null ? texto.hashCode() : 0);
        result = 31 * result + (fechaEnvio != null ? fechaEnvio.hashCode() : 0);
        result = 31 * result + (fechaResolucion != null ? fechaResolucion.hashCode() : 0);
        result = 31 * result + (aceptada != null ? aceptada.hashCode() : 0);
        result = 31 * result + (recomendador != null ? recomendador.hashCode() : 0);
        result = 31 * result + (recomendado != null ? recomendado.hashCode() : 0);
        result = 31 * result + (work != null ? work.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Recomendation{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaResolucion=" + fechaResolucion +
                ", aceptada=" + aceptada +
                ", recomendador=" + recomendador +
                ", recomendado=" + recomendado +
                ", work=" + work +
                ", company=" + company +
                '}';
    }
}
