package com.api.controller;

import com.api.model.Rol;
import com.api.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService service;

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Rol> lista = service.findAll();
        if (lista.isEmpty()) return new ResponseEntity<>("Lista de Roles Vacía", HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Rol item = service.findById(id);
        if (item == null) return new ResponseEntity<>("No se encontró el Rol con id: "+id, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Rol rol){
        String name = service.create(rol);
        return new ResponseEntity<>("Rol "+name+" Creado con éxito", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Rol rol, @PathVariable Long id){
        Rol item = service.findById(id);
        if (item == null) return new ResponseEntity<>("No se encontró el Rol con id: "+id, HttpStatus.NOT_FOUND);
        else{
            String name = service.update(rol, id);
            return new ResponseEntity<>("Rol "+name+" Actualizado con éxito", HttpStatus.OK);
        }
    }
}