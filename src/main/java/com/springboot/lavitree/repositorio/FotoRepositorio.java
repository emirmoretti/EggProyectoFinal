/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.repositorio;


import com.springboot.lavitree.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emir
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
}
