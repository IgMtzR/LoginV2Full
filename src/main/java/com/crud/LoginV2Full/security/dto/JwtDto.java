package com.crud.LoginV2Full.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
//esta clae permiteestablecer un orden a los valores de retorno que el cliente debera de utilizar para realizar
//una correcta autenticacion
public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String nombreUsuario;
    //coleccion de dato con un valor no referenciado, puede se cualquiera
    private Collection<? extends GrantedAuthority> authorities;

    //constructor parametrizado de la clase
    public JwtDto(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.authorities = authorities;
    }

    //conjunto de setters ay getters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}