/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.controladores;

import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.enumeraciones.Paises;
import com.springboot.lavitree.repositorio.UsuarioRepositorio;
import com.springboot.lavitree.servicios.UsuarioServicio;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Emir
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }

        try {
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Usuario usuario = respuesta.get();
                model.addAttribute("perfil", usuario);
                model.put("paises", Paises.values());
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.put("paises", Paises.values());
        }
        return "editar-perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil")
    public String registrar(ModelMap model, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String biografia, Integer telefono, String facebook, String instagram, String twitter, String linkedin, @RequestParam Paises pais) {

        Usuario usuario = null;

        Usuario login = (Usuario) session.getAttribute("usuariosession");

        try {
            if (login == null || !login.getId().equals(id)) { //!login.getid.equals(id) evita que un usuario hackee a otro cambiando el id en el form
                return "redirect:/index";
            }
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {
                usuario = respuesta.get();
            }
            usuarioServicio.modificar(id, nombre, apellido, mail, clave1, archivo, clave2, telefono, biografia, pais, facebook, instagram, twitter, linkedin);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/login";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("perfil", usuario);
            model.put("paises", Paises.values());
            return "editar-perfil.html";
        }
    }
}
