package com.api.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String correo;

    private String password;

    @ManyToMany
    private Set<Rol> rol;

}
