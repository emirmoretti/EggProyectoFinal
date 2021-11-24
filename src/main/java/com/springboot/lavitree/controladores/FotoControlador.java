/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.controladores;

import com.springboot.lavitree.entidades.Foto;
import com.springboot.lavitree.entidades.Obra;
import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.excepciones.ErrorServicio;
import com.springboot.lavitree.repositorio.UsuarioRepositorio;
import com.springboot.lavitree.servicios.FotoServicio;
import com.springboot.lavitree.servicios.ObraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ObraServicio obraServicio;
    @Autowired
    private FotoServicio fotoServicio;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) throws ErrorServicio {

        try {
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            Usuario usuario = respuesta.get();
            if (usuario.getFoto() == null) {
                throw new ErrorServicio("El usuario no tiene una foto");
            }
            byte[] foto = usuario.getFoto().getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorServicio e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/obra/{id}")
    public ResponseEntity<byte[]> fotoObra(@PathVariable String id) throws ErrorServicio {

        try {
            Obra obra = obraServicio.buscarPorId(id);
            if (obra.getFoto() == null) {
                throw new ErrorServicio("La mascota no tiene una foto");
            }
            byte[] foto = obra.getFoto().get(0).getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorServicio e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/obra/galeria/{id}")
    public ResponseEntity<byte[]> fotosObra(@PathVariable String id) throws ErrorServicio, Exception {
        try {
            Foto fotito = fotoServicio.buscarPorId(id);
            byte[] foto = fotito.getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorServicio e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
