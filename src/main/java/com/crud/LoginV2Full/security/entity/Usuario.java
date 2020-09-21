package com.crud.LoginV2Full.security.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;

    //creacion de tabla pivot para la base de datos
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)//estableciendo relacion de muchos a muchos
    /*
    estableciendo nombre de tabla 'usuario_rol'
    se establece la columna referenciada a usuario 'joinColumns = @JoinColumn(name = "usuario_id")'
    se establece la columna referenciada a rol 'inverseJoinColumns = @JoinColumn(name = "rol_id")'
     */
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    //se genera una coleccion de parametros que no pueden repetirse, dicha coleccion contendra los roles
    private Set<Rol> roles = new HashSet<>();

    //constructor no parametrizado
    public Usuario() {
    }

    //constructor parametrizado
    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}