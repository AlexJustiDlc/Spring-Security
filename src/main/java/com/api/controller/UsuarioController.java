package com.api.controller;

import com.api.model.Usuario;
import com.api.service.UsuarioService;
import com.api.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Usuario> lista = service.findAll();
        if (lista.isEmpty()) return new ResponseEntity<>("Lista de Usuarios Vacía", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(MapperUtil.usuarios(lista), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Usuario item = service.findById(id);
        if (item == null) return new ResponseEntity<>("No se encontró al Usuario con id: "+id, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario){
        String name = service.create(usuario);
        return new ResponseEntity<>("Usuario "+name+" Creado con éxito", HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable Long id){
        Usuario item = service.findById(id);
        if (item == null) return new ResponseEntity<>("No se encontró al Usuario con id: "+id, HttpStatus.NOT_FOUND);
        else {
            String name = service.update(usuario, id);
            return new ResponseEntity<>("Usuario "+name+" Actualizado con éxito", HttpStatus.OK);
        }
    }
}
