package com.api.util;

import com.api.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {
    public static List<UsuarioDto> usuarios(List<Usuario> items){
        List<UsuarioDto> lista = new ArrayList<>();
        for (Usuario usuario : items){
            UsuarioDto mapper = new UsuarioDto();
            mapper.setId        (usuario.getId());
            mapper.setNombre    (usuario.getNombre());
            mapper.setApellido  (usuario.getApellido());
            mapper.setCorreo     (usuario.getCorreo());
            mapper.setRol       (usuario.getRol());
            lista.add(mapper);
        }
        return lista;
    }
}
