/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.lavitree.servicios;


import com.springboot.lavitree.entidades.Foto;
import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.enumeraciones.Paises;
import com.springboot.lavitree.excepciones.ErrorServicio;
import com.springboot.lavitree.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private EmailSenderServicio emailSenderServicio;

    @Transactional
    public void registrar(String nombre, String apellido, String mail, String clave, MultipartFile archivo, String clave2, Integer telefono, String biografia, Paises pais, String facebook, String instagram, String twitter, String linkedin) throws ErrorServicio, Exception {
        validarEmailDisponible(mail);
        validar(nombre, apellido, mail, clave, clave2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setPaises(pais);
        usuario.setBiografia(biografia);
        usuario.setTelefono(telefono);
        usuario.setFacebook(facebook);
        usuario.setInstagram(instagram);
        usuario.setTwitter(twitter);
        usuario.setLinkedin(linkedin);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date());
        Foto foto = fotoServicio.guardar(archivo);

        usuario.setFoto(foto);
//        emailSenderServicio.envioMail(mail, "Bienvenido a La vitreé", "Ya podes disfrutar todas nuestras obras");
        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String mail, String clave, MultipartFile archivo, String clave2, Integer telefono, String biografia, Paises paises, String facebook, String instagram, String twitter, String linkedin) throws ErrorServicio, Exception {
        validar(nombre, apellido, mail, clave, clave2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setMail(mail);
            usuario.setPaises(paises);
            usuario.setBiografia(biografia);
            usuario.setTelefono(telefono);
            usuario.setFacebook(facebook);
            usuario.setInstagram(instagram);
            usuario.setTwitter(twitter);
            usuario.setLinkedin(linkedin);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);

            String idFoto = null;

            if (usuario.getFoto() != null && !archivo.isEmpty()) {
                idFoto = usuario.getFoto().getId();
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                usuario.setFoto(foto);
            }            
            usuarioRepositorio.save(usuario);
//            emailSenderServicio.envioMail(mail, "Se actualizaron tus datos en La vitreé", "Logueate para chequear que tus datos se modificaron correctamente.");

        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }

    }

    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepositorio.save(usuario);
//            emailSenderServicio.envioMail(usuario.getMail(), "Oh no! te vas de  La vitreé", "Nos apena mucho que te vayas de nuestra galeria de arte virtual, siempre te esperamos con los brazos abiertos!");
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public Usuario buscarPorId(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("El usuario solicitado no existe.");
        }
    }

    public void validar(String nombre, String apellido, String mail, String clave, String clave2) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario esta vacio.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario esta vacio.");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail del usuario esta vacio.");
        }
        if (clave == null || clave.isEmpty() || clave.length() < 5) {
            throw new ErrorServicio("La clave del usuario no puede ser vacia o ser menor de 5 digitos");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves no son iguales");
        }
        if (!ValidarEmail(mail)) {
            throw new ErrorServicio("El email es incorrecto pa");
        }
    }

    public void validarEmailDisponible(String mail) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {
            throw new ErrorServicio("Email ya utilizado en la pagina");
        }
    }

    public Boolean ValidarEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Boolean validacion = false;
        validacion = Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
        return validacion;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(); //Capturo los datos de la sesion iniciada y las muestro 
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }

}
