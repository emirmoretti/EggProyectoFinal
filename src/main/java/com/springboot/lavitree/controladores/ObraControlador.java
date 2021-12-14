/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.controladores;

import com.springboot.lavitree.entidades.Foto;
import com.springboot.lavitree.entidades.Obra;
import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.enumeraciones.Estilo;
import com.springboot.lavitree.enumeraciones.TipoDeObra;
import com.springboot.lavitree.excepciones.ErrorServicio;
import com.springboot.lavitree.servicios.FotoServicio;
import com.springboot.lavitree.servicios.ObraServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Emir
 */
@Controller
@RequestMapping("/obra")
public class ObraControlador {

    @Autowired
    private ObraServicio obraServicio;
    @Autowired
    private FotoServicio fotoServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/mis-obras")
    public String misObras(HttpSession session, ModelMap model) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
        List<Obra> obras = obraServicio.buscarObrasPorUsuario(login.getId());
        model.put("obras", obras);
        return "mis-obras";
    }

    @GetMapping("/ver-obra")
    public String miObra(ModelMap model, @RequestParam(required = false) String id) {

        Obra obra = new Obra();
        if (id != null && !id.isEmpty()) {
            try {
                obra = obraServicio.buscarPorId(id);
            } catch (ErrorServicio ex) {
                Logger.getLogger(ObraControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Foto> fotos = obra.getFoto();
        model.put("obra", obra);
        model.put("foto", fotos);
        return "ver-obra";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/delete")
    public String eliminarObra(@RequestParam String id) {
        obraServicio.deleteById(id);
        return "redirect:/inicio";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/agregar-obra")
    public String agregarObras(HttpSession session, ModelMap model) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        model.put("tipos", TipoDeObra.values());
        model.put("estilos", Estilo.values());
        return "obra-form";
    }

    @GetMapping("/editar-obra")
    public String editarObra(HttpSession session, ModelMap model, @RequestParam(required = false) String id) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }

        Obra obra = new Obra();
        if (id != null && !id.isEmpty()) {
            try {
                obra = obraServicio.buscarPorId(id);
            } catch (ErrorServicio ex) {
                Logger.getLogger(ObraControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Foto> fotos = obra.getFoto();
        model.put("obra", obra);
        model.put("fotos", fotos);
        model.put("tipos", TipoDeObra.values());
        model.put("estilos", Estilo.values());

        return "obra-modificar";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-obra")
    public String actualizar(ModelMap model, HttpSession session, MultipartFile archivo1, List<MultipartFile> archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String descripcion, Integer precio, Estilo estilo, TipoDeObra tipoDeObra) throws Exception {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/index";
        }

        try {
            if (id == null || id.isEmpty()) {
                obraServicio.agregarObra(archivo, login.getId(), nombre, descripcion, precio, estilo, tipoDeObra);
            } else {
                obraServicio.modificar(archivo1, login.getId(), id, nombre, descripcion, precio, estilo, tipoDeObra);
            }

            return "index";
        } catch (Exception e) {
            Obra obra = new Obra();
            obra.setId(id);
            obra.setNombre(nombre);
            model.put("error", e.getMessage());
            return "index";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/eliminar-foto")
    public String eliminarFoto(@RequestParam(required = true) String id, HttpSession session) throws ErrorServicio, Exception {
        //Usuario usuario = usuarioServicio.buscarPorId(idUser);
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        //foto.usuario.id == login.usuario.id
        //Foto foto = fotoServicio.buscarPorId(id)
        fotoServicio.deleteById(id);
        return "redirect:/obra/mis-obras";
    }

    @GetMapping("/lista-obras")
    public String buscarObrasTipo(ModelMap model, @RequestParam(value = "tipo") TipoDeObra tipo) throws ErrorServicio, Exception {
        System.out.println("TIPO ??-----" + tipo);
        try {
            if (tipo != null) {
                model.put("obras", obraServicio.buscarObrasPorTipo(tipo));
            } else {
                model.put("obras", obraServicio.todasLasObras());
            }
            model.put("tipos", TipoDeObra.values());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "explorar";
    }
}
