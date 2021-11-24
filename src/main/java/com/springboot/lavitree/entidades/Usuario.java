/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.springboot.lavitree.enumeraciones.Paises;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Emir
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "{Se necesita email valido}", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String mail;
    @NotBlank
    private String clave;
    private Integer telefono;
    private String biografia;
    @Enumerated(EnumType.STRING)
    private Paises paises;
    private String facebook;
    private String instagram;
    private String linkedin;
    private String twitter;
    @OneToOne
    private Foto foto;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the telefono
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * @param biografia the biografia to set
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * @return the paises
     */
    public Paises getPaises() {
        return paises;
    }

    /**
     * @param paises the paises to set
     */
    public void setPaises(Paises paises) {
        this.paises = paises;
    }

    /**
     * @return the facebook
     */
    public String getFacebook() {
        return facebook;
    }

    /**
     * @param facebook the facebook to set
     */
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    /**
     * @return the instagram
     */
    public String getInstagram() {
        return instagram;
    }

    /**
     * @param instagram the instagram to set
     */
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    /**
     * @return the linkedin
     */
    public String getLinkedin() {
        return linkedin;
    }

    /**
     * @param linkedin the linkedin to set
     */
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    /**
     * @return the twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter the twitter to set
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    /**
     * @return the foto
     */
    public Foto getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    /**
     * @return the alta
     */
    public Date getAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(Date alta) {
        this.alta = alta;
    }

    /**
     * @return the baja
     */
    public Date getBaja() {
        return baja;
    }

    /**
     * @param baja the baja to set
     */
    public void setBaja(Date baja) {
        this.baja = baja;
    }

}
