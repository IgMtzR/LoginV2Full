package com.crud.LoginV2Full.security.dto;

import java.util.HashSet;
import java.util.Set;

/*esta clase establece la estructura de los valores resividos para generar un nuevo usuario
* tambien crea una coleccion de elementos que no puede contener elementos duplicados con
* el fin de parametrizar los Roles requeridos sin errores de repeticion
* */
public class NuevoUsuario {

    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    //colleccion de parametros no duplicados
    private Set<String> roles = new HashSet<>();

    //conjunto de setters y getters
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}