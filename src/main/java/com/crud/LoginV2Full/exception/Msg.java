package com.crud.LoginV2Full.exception;

public class Msg {//esta clase solo se encargara del manejo de mensajes para el retorno de estos en archios JSON
    private  String msg;//defino una variable de tipo cadena que contendra el mensaje

    public Msg(String msg) {//definiendo el constructor, donde la clase resivira una cadena de caracteres
        this.msg = msg;//la cadena de caracteres se le asigna a la variable
    }

    public String getMsg() {//metodo get que retorna una cadena que contiene a la variable msg
        return msg;
    }

    public void setMsg(String msg) {//metodo set
        this.msg = msg;// permite asignar a la variable msg de la clase una cadena de caracteres desde el exterior
    }
}
