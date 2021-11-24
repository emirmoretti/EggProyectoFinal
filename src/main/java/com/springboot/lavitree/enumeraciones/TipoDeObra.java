/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.enumeraciones;

/**
 *
 * @author Emir
 */
public enum TipoDeObra {
    PINTURA("pintura"), ESCULTURA("escultura"), FOTOGRAFIA("fotografia"), ILUSTRACION("ilustracion"),
    DIGITAL("digital"), INTERVENCION("intervencion");

    private final String displayValue;

    TipoDeObra(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
