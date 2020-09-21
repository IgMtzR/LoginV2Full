package com.crud.LoginV2Full.security.dto;

//esta clase establece el tipo de datos que se resiviran a partir de un logeo
public class LoginUsuario {

    private String nombreUsuario;
    private String password;

    //conjunto de setters y getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}