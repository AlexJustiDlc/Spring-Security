package com.api.util;

import com.api.model.Rol;
import com.api.model.Usuario;
import com.api.service.RolService;
import com.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Data {

    @Autowired
    private UsuarioService serviceU;

    @Autowired
    private RolService serviceR;

    List<Usuario> usuarios = new ArrayList<>();

    @PostConstruct
    public void data(){
        if (usuarios.isEmpty()){
            Rol rol = new Rol();
            rol.setDescripcion("ADMIN");
            serviceR.create(rol);

            Rol role = new Rol();
            role.setDescripcion("USER");
            serviceR.create(role);

            Set<Rol> roles = new HashSet<>();
            roles.add(rol);
            roles.add(role);

            Usuario usuario = new Usuario();
            usuario.setNombre("Alex Fernando");
            usuario.setApellido("Justiniano De la cruz");
            usuario.setCorreo("alex@gmail.com");
            usuario.setPassword("alex123");
            usuario.setRol(roles);

            serviceU.create(usuario);
        }
        else System.out.println("Ya hay Usuarios en el Sistema");
    }
}