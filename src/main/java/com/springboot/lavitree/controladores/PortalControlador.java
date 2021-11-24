package com.springboot.lavitree.controladores;

import com.springboot.lavitree.entidades.Obra;
import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.enumeraciones.Paises;
import com.springboot.lavitree.enumeraciones.TipoDeObra;
import com.springboot.lavitree.servicios.EmailSenderServicio;
import com.springboot.lavitree.servicios.ObraServicio;
import com.springboot.lavitree.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ObraServicio obraServicio;
    @Autowired
    private EmailSenderServicio emailSenderServicio;

    @GetMapping("/")
    public String index(ModelMap model) {
        List<Obra> obras = obraServicio.todasLasObras();
        model.put("obras", obras);
        return "index.html";
    }

    @GetMapping("/explorar")
    public String explorador(ModelMap model) {
        List<Obra> obras = obraServicio.todasLasObras();
        model.put("tipos", TipoDeObra.values());
        model.put("obras", obras);
        return "explorar";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap model) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        List<Obra> obras = obraServicio.todasLasObras();
        model.put("tipos", TipoDeObra.values());
        model.put("obras", obras);
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo) {
        if (error != null) {
            modelo.put("error1", "usuario o clave incorrectos.");
        }
        if (logout != null) {
            modelo.put("logout", "Ha salido correctamente de la plataforma");
        }
        modelo.put("paises", Paises.values());
        return "login.html";
    }

    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
        modelo.put("paises", Paises.values());
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String mail, @RequestParam String clave, @RequestParam String clave2, Integer telefono, String biografia,
            @RequestParam MultipartFile archivo, @RequestParam Paises pais,
            String facebook, String instagram,
            String twitter, String linkedin) {
        try {
            System.out.println("TIPO DE ARCHIVO -------------------- " + archivo.getContentType());
            usuarioServicio.registrar(nombre, apellido, mail, clave, archivo, clave2, telefono, biografia, pais, facebook, instagram, twitter, linkedin);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("paises", Paises.values());
            modelo.put("mail", mail);
            modelo.put("clave1", clave);
            modelo.put("clave2", clave2);

            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "registro.html";
        }
        modelo.put("titulo", "bienvenido al webart");
        modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "exitog.html";
    }

    @GetMapping("/contacto")
    public String contacto(ModelMap model) {
        return "contacto";
    }

    @PostMapping("/envioContacto")
    public String envioContacto(ModelMap modelo, @RequestParam String nombre, @RequestParam String mail, @RequestParam String asunto, @RequestParam String mensaje) {
        String bodyFinal;
        bodyFinal = nombre + " " + "quiere contactar con nosotros, sus datos son: " + "\n"
                + "Email: " + mail + "\n"
                + "Y dice: " + mensaje;
        emailSenderServicio.contacto("lavitreegg@gmail.com", asunto, bodyFinal);
        return "index";
    }
    
    @PostMapping("/envioContactoVendedor")
    public String envioContactoA(ModelMap modelo, @RequestParam String mailA,@RequestParam String mailI) {
        String bodyFinal;
        bodyFinal = "Un usuario quiere contactar con usted por su obra, su mail es: " + "\n"
                + "Email: " + mailI ;
        emailSenderServicio.contactoConVendedor(mailA,bodyFinal);
        return "index";
    }
    
    
}
