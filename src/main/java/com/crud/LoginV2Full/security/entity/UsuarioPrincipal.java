package com.crud.LoginV2Full.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {

    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    //generando una coleccion de parametros GrantedAuthority que contendra la lista de permisos "roles" del usuario
    private Collection<? extends GrantedAuthority> authorities;

    //constructor parametrizado de la clase
    public UsuarioPrincipal(String nombre, String nombreUsuario, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //estableciendo un metodo bulid para generar el usuario principal
    public static UsuarioPrincipal build(Usuario usuario){
        //generando una lista de los datos nesesarios para realizar autotizaciones
        List<GrantedAuthority> authorities = usuario
                .getRoles()
                .stream()
                .map(
                        //funcion landa consigue los roles de una colecccion
                        rol -> new SimpleGrantedAuthority(
                                rol.getRolNombre().name()
                        )
                ).collect(Collectors.toList());

        return new UsuarioPrincipal(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities);
    }

    //conjunto de setters y getters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}