package com.api.service;

import com.api.model.Rol;
import com.api.repository.RolRepository;
import com.api.util.errors.exception.BadRequest;
import com.api.util.errors.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository repository;

    public List<Rol> findAll(){
        List<Rol> lista = (List<Rol>) repository.findAll();
        if (lista.isEmpty()) throw new NotFound("No se encontraron registros de Roles");

        return lista;
    }

    public Rol findById(Long id){
        if (!repository.existsById(id)) throw new NotFound("No se encontró Rol con id: "+id);

        return repository.findById(id).orElse(null);
    }

    public String create(Rol rol) throws BadRequest {
        if (rol.getDescripcion().isEmpty()) throw new BadRequest("Ingrese un Nombre para el Rol");
        if (rol.getDescripcion() == null) throw new BadRequest("Ingrese un Nombre para el Rol");
        rol.setDescripcion(rol.getDescripcion());

        Rol newRol = repository.save(rol);

        String nameRol = newRol.getDescripcion();

        return nameRol;
    }

    public String update(Rol rol, Long id) throws BadRequest{
        if (!repository.existsById(id)) throw new NotFound("No se encontraró Rol con id: "+id);
        Rol updRol = repository.findById(id).orElse(null);

        if (rol.getDescripcion() == null) throw new BadRequest("Ingrese un Nombre para el Rol");
        if (rol.getDescripcion().isEmpty()) throw new BadRequest("Ingrese un Nombre para el Rol");
        updRol.setDescripcion(rol.getDescripcion());

        Rol rolUpdate = repository.save(updRol);

        String nameRolUpdate = rolUpdate.getDescripcion();

        return nameRolUpdate;
    }
}