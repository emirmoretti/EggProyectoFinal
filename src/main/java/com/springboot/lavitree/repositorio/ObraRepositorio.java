/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.repositorio;

import com.springboot.lavitree.entidades.Obra;
import com.springboot.lavitree.enumeraciones.TipoDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Emir
 */
@Repository
public interface ObraRepositorio extends JpaRepository<Obra, String> {

    @Query("SELECT c FROM Obra c WHERE c.usuario.id = :id AND c.baja is NULL")
    public List<Obra> buscarObrasPorUsuario(@Param("id") String id);
    /*
    @Query("SELECT c FROM Obra c WHERE c.tipoDeObra = grupo1.webArte.enumeraciones.TipoDeObra.PINTURA")
    List<Obra> findAllByTipo(@Param("q") String q);*/
    @Query("SELECT c FROM Obra c WHERE c.tipoDeObra = :tipo")
    public List<Obra> findAllByTipo(@Param("tipo") TipoDeObra tipo);
}
