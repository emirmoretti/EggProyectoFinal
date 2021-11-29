package com.springboot.lavitree.servicios;

import com.springboot.lavitree.entidades.Foto;
import com.springboot.lavitree.entidades.Obra;
import com.springboot.lavitree.entidades.Usuario;
import com.springboot.lavitree.enumeraciones.Estilo;
import com.springboot.lavitree.enumeraciones.TipoDeObra;
import com.springboot.lavitree.excepciones.ErrorServicio;
import com.springboot.lavitree.repositorio.ObraRepositorio;
import com.springboot.lavitree.repositorio.UsuarioRepositorio;
import java.util.Date;
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
public class ObraServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ObraRepositorio obraRepositorio;
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void agregarObra(List<MultipartFile> archivo, String idUsuario, String nombre, String descripcion, Integer precio, Estilo estilo, TipoDeObra tipoDeObra) throws ErrorServicio, Exception {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        //validar(nombre, descripcion);
        Obra obra = new Obra();
        obra.setNombre(nombre);
        obra.setDescripcion(descripcion);
        obra.setPrecio(precio);
        obra.setAlta(new Date());
        obra.setTipoDeObra(tipoDeObra);
        obra.setEstilo(estilo);
        obra.setUsuario(usuario);
        if (!archivo.isEmpty() && archivo != null && !archivo.get(0).getContentType().equalsIgnoreCase("application/octet-stream")) {
            System.out.println("------asdasdasd22");
            List<Foto> fotos = fotoServicio.guardarFotos(archivo);
            obra.setFoto(fotos);
        }
        obraRepositorio.save(obra);
    }

    @Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String idObra, String nombre, String descripcion, Integer precio, Estilo estilo, TipoDeObra tipoDeObra) throws ErrorServicio, Exception {
        // validar(nombre, descripcion);
        System.out.println("---------------------MODIFICAR OBRA ----------------");
        Optional<Obra> respuesta = obraRepositorio.findById(idObra);
        if (respuesta.isPresent()) {
            Obra obra = respuesta.get();
            if (obra.getUsuario().getId().equals(idUsuario)) {
                System.out.println("ID OBRA ---------" + idObra);
                System.out.println("ID USUARIO -------" + idUsuario);
                obra.setNombre(nombre);
                obra.setPrecio(precio);
                obra.setDescripcion(descripcion);
                obra.setTipoDeObra(tipoDeObra);
                obra.setEstilo(estilo);
                System.out.println("tamaño del array--------" + obra.getFoto().size());
                if (!archivo.isEmpty()) {
                    Foto fotoNueva = fotoServicio.guardar(archivo); // el bardo //
                    obra.getFoto().add(fotoNueva);
                }
                obraRepositorio.save(obra);
            } else {
                System.out.println("-----------------------ELSE");
                throw new ErrorServicio("No tiene permisos suficientes para realizar la op");
            }
        } else {
            System.out.println("-----------------------ELSE2");
            throw new ErrorServicio("No existe ese id de Obra");
        }
    }

    @Transactional
    public void eliminar(String idUsuario, String idObra) throws ErrorServicio {
        Optional<Obra> respuesta = obraRepositorio.findById(idObra);
        if (respuesta.isPresent()) {
            Obra obra = respuesta.get();
            if (obra.getUsuario().getId().equals(idUsuario)) {
                obra.setBaja(new Date());
                obraRepositorio.save(obra);
            }
        } else {
            throw new ErrorServicio("No existe esa obra");
        }
    }

    public void validar(String nombre, String descripcion) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            /*Ver si validar precio Y TipodeObra*/
            throw new ErrorServicio("Ingrese un nombre valido");
        }
        if (descripcion == null || descripcion.length() > 150) {
            throw new ErrorServicio("Ingrese la descripcion de la obra");
        }

    }
    @Transactional(readOnly=true)
    public Obra buscarPorId(String id) throws ErrorServicio {
        Optional<Obra> respuesta = obraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("La obra solicitade no existe.");
        }
    }

    @Transactional(readOnly=true)
    public List<Obra> buscarObrasPorUsuario(String id) {
        return obraRepositorio.buscarObrasPorUsuario(id);
    }

    @Transactional(readOnly=true)
    public List<Obra> todasLasObras() {
        return obraRepositorio.findAll();
    }
    @Transactional(readOnly=true)
    public List<Obra> buscarObrasPorTipo(TipoDeObra tipo) {
        return obraRepositorio.findAllByTipo(tipo);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Obra> optional = obraRepositorio.findById(id);
        if (optional.isPresent()) {
            obraRepositorio.delete(optional.get());
        }
    }
}
