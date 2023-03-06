package com.ejercicio2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "alumnado")
@EntityListeners(AuditingEntityListener.class)

public class Alumno implements Serializable {
    public Alumno (int id, String nombre, String telefono, String email, float ad, float di) {
        Id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.ad = ad;
        this.di = di;
    }

    public Alumno () {
    }

    @Id
    @Column(name = "id")
    int Id;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "telefono")
    String telefono;
    @Column(name = "email")
    String email;
    @Column(name = "ad")
    float ad;
    @Column(name = "di")
    float di;

    public int getId () {
        return Id;
    }

    public void setId (int id) {
        Id = id;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono () {
        return telefono;
    }

    public void setTelefono (String telefono) {
        this.telefono = telefono;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public float getAd () {
        return ad;
    }

    public void setAd (float ad) {
        this.ad = ad;
    }

    public float getDi () {
        return di;
    }

    public void setDi (float di) {
        this.di = di;
    }
}

