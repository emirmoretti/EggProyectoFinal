/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.entidades;

import com.springboot.lavitree.enumeraciones.Estilo;
import com.springboot.lavitree.enumeraciones.TipoDeObra;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Emir
 */
@Entity
public class Obra {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    @ManyToOne
    private Usuario usuario;
    private String descripcion;
    private Integer precio;
    private Integer meGusta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    @Enumerated(EnumType.STRING)
    private Estilo estilo;
    @Enumerated(EnumType.STRING)
    private TipoDeObra tipoDeObra;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idObraFotos")
    private List<Foto> foto;


    public Obra() {
    }

    public Obra(String id, String nombre, Usuario usuario, String descripcion, Integer precio, Integer meGusta, Date alta, Date baja, Estilo estilo, TipoDeObra tipoDeObra, List<Foto> foto) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.precio = precio;
        this.meGusta = meGusta;
        this.alta = alta;
        this.baja = baja;
        this.estilo = estilo;
        this.tipoDeObra = tipoDeObra;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Integer meGusta) {
        this.meGusta = meGusta;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public TipoDeObra getTipoDeObra() {
        return tipoDeObra;
    }

    public void setTipoDeObra(TipoDeObra tipoDeObra) {
        this.tipoDeObra = tipoDeObra;
    }

    public List<Foto> getFoto() {
        return foto;
    }

    public void setFoto(List<Foto> foto) {
        this.foto = foto;
    }
}
