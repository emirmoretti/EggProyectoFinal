/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.servicios;

import com.springboot.lavitree.entidades.Foto;
import com.springboot.lavitree.excepciones.ErrorServicio;
import com.springboot.lavitree.repositorio.FotoRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Emir
 */
@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio, Exception {
        if (!archivo.isEmpty()) {
            try {
                validar(archivo);
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public List<Foto> guardarFotos(List<MultipartFile> archivo) throws ErrorServicio, Exception {
        try {
            List<Foto> fotos = new ArrayList();
            for (MultipartFile fotito : archivo) {
                validar(fotito);
                Foto foto = new Foto();
                foto.setMime(fotito.getContentType());
                foto.setNombre(fotito.getName());
                foto.setContenido(fotito.getBytes());
                fotos.add(foto);
            }
            return fotoRepositorio.saveAll(fotos);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio, Exception {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        }
        return null;
    }

    public void validar(MultipartFile archivo) throws ErrorServicio {
        String MimeType = archivo.getContentType();
        if (!"image/png".equals(MimeType) && !"image/jpeg".equals(MimeType) && !"image/jpg".equals(MimeType)) {
            throw new ErrorServicio("No se puede subir un archivo del tipo " + MimeType);
        } else {
            System.out.println("imagen subida");
        }
    }

    @Transactional(readOnly=true)
    public Foto buscarPorId(String idFoto) throws ErrorServicio, Exception {
        Foto foto = new Foto();
        if (idFoto != null) {
            Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
            if (respuesta.isPresent()) {
                foto = respuesta.get();
            }
        }
        return foto;
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Foto> optional = fotoRepositorio.findById(id);
        if (optional.isPresent()) {
            fotoRepositorio.delete(optional.get());
        }
    }
}
