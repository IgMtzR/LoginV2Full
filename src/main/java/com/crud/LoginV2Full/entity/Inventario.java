package com.crud.LoginV2Full.entity;

import lombok.Data;

import javax.persistence.*;

@Entity//Estableciendo el tipo entidad
@Data//setters y getters mediante lombok
@Table(name = "inventario")//estableciendo la tabla en l abase de datos
public class Inventario {

    @Id//asignando typo private a 'id'
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id auto generado
    @Column(name = "id_inventario")//columna dentro de la base de datos
    private  int id;

    @Column(name = "codigo")//columna dentro de la base de datos
    private String codigo;

    @Column(name = "tipo")//columna dentro de la base de datos
    private String tipo;

    @Column(name = "descripcion")//columna dentro de la base de datos
    private String descripcion;

    @Column(name = "estado")//columna dentro de la base de datos
    private String estado;

    @Column(name = "departamento")//columna dentro de la base de datos
    private String departamento;

    public Inventario() {//construtor vacio, decidi implementarlo asi y no por lombok
    }

    //constructor inicializado tambien decidi crearlo sin lombok
    //no agrege id por que este genera un valor automatico mediante el '@GeneratedValue(strategy = GenerationType.IDENTITY)'
    public Inventario(String codigo, String tipo, String descripcion, String estado, String departamento) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.departamento = departamento;
    }
}
