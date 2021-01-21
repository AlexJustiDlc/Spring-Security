package com.api.service;

import com.api.model.Rol;
import com.api.model.Usuario;
import com.api.repository.RolRepository;
import com.api.repository.UsuarioRepository;
import com.api.util.errors.exception.BadRequest;
import com.api.util.errors.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RolRepository repositoryR;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public List<Usuario> findAll(){

        List<Usuario> lista = (List<Usuario>) repository.findAll();
        if (lista.isEmpty()) throw new NotFound("No se encontraron registros de Usuarios");

        return lista;
    }

    public Usuario findById(Long id){
        if (!repository.existsById(id)) throw new NotFound("No se encontró Usuario con id: "+id);

        return repository.findById(id).orElse(null);
    }

    public String create(Usuario usuario) {
        if (usuario.getNombre().isEmpty()) throw new BadRequest("Ingrese un Nombre para el Usuario");
        if (usuario.getNombre() == null) throw new BadRequest("Ingrese un Nombre para el Usuario");
        usuario.setNombre(usuario.getNombre());

        if (usuario.getApellido().isEmpty()) throw new BadRequest("Ingrese un Apellido para el Usuario");
        if (usuario.getApellido() == null) throw new BadRequest("Ingrese un Apellido para el Usuario");
        usuario.setApellido(usuario.getApellido());

        if (usuario.getCorreo().isEmpty()) throw new BadRequest("Ingrese un Correo para el Usuario");
        if (usuario.getCorreo() == null) throw new BadRequest("Ingrese un Correo para el Usuario");
        Usuario correo = repository.findByCorreo(usuario.getCorreo());
        if (correo != null) throw new BadRequest("El Correo ya existe");
        usuario.setCorreo(usuario.getCorreo());

        if (usuario.getPassword().isEmpty()) throw new BadRequest("Ingrese una Contraseña para el Usuario");
        if (usuario.getPassword() == null) throw new BadRequest("Ingrese una Contraseña para el Usuario");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Set<Rol> roles = usuario.getRol();
        for (Rol rol : roles){
            Long idRol = rol.getId();
            if (idRol == null) throw new BadRequest("Ingrese el Rol para el Usuario");
            if (roles != null){
                Boolean rolUsuario = repositoryR.existsById(idRol);
                if (!rolUsuario)throw new BadRequest("No se encontró Rol con id: "+idRol);
                usuario.setRol(roles);
            }
        }

        Usuario newUsuario = repository.save(usuario);

        String nameUser = newUsuario.getNombre()+", "+newUsuario.getApellido();

        return nameUser;
    }

    public String update(Usuario usuario, Long id){
        if (!repository.existsById(id)) throw new NotFound("No se encontró Usuario con id: "+id);
        Usuario updUsuario = repository.findById(id).orElse(null);

        if (usuario.getNombre() == null) throw new BadRequest("Ingrese un Nombre para el Usuario");
        if (usuario.getNombre().isEmpty()) throw new BadRequest("Ingrese un Nombre para el Usuario");
        updUsuario.setNombre(usuario.getNombre());

        if (usuario.getApellido() == null) throw new BadRequest("Ingrese un Apellido para el Usuario");
        if (usuario.getApellido().isEmpty()) throw new BadRequest("Ingrese un Apellido para el Usuario");
        updUsuario.setApellido(usuario.getApellido());

        if (usuario.getCorreo() == null) throw new BadRequest("Ingrese un Correo para el Usuario");
        if (usuario.getCorreo().isEmpty()) throw new BadRequest("Ingrese un Correo para el Usuario");
        if (usuario.getCorreo().equals(updUsuario.getCorreo())) updUsuario.setCorreo(updUsuario.getCorreo());
        else {
            Usuario correo = repository.findByCorreo(usuario.getCorreo());
            if (correo != null) throw new BadRequest("El Correo ya existe");
            updUsuario.setCorreo(usuario.getCorreo());
        }

        if (usuario.getPassword() == null) throw new BadRequest("Ingrese una Contraseña para el Usuario");
        if (usuario.getPassword().isEmpty()) throw new BadRequest("Ingrese una Contraseña para el Usuario");
        updUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Set<Rol> roles = usuario.getRol();
        for (Rol rol : roles){
            Long idRol = rol.getId();
            if (idRol == null) throw new BadRequest("Ingrese el Rol para el Usuario");
            if (roles != null){
                Boolean rolUsuario = repositoryR.existsById(idRol);
                if (!rolUsuario)throw new BadRequest("No se encontró Rol con id: "+idRol);
                updUsuario.setRol(roles);
            }
        }

        Usuario usuarioUpdate = repository.save(updUsuario);

        String nameUsuarioUpdate = usuarioUpdate.getNombre()+", "+usuarioUpdate.getApellido();

        return nameUsuarioUpdate;
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        Usuario usuario = repository.findByCorreo(username);
        if (usuario == null){
            System.out.println("Error de Inicio de Sesión....!!!!\nUsuario "+username+" No Encontrado...");
            throw new UsernameNotFoundException("Usuario "+username+" no Encontrado");
        }

        Set<GrantedAuthority> roles = new HashSet<>();
        for (Rol rol : usuario.getRol()){
            roles.add(new SimpleGrantedAuthority("ROLE_"+rol.getDescripcion()));
        }

        UserDetails userDetails = new User(usuario.getCorreo(), usuario.getPassword(), roles);
        System.out.println("roles de "+username+": "+ roles);

        return userDetails;
    }
}