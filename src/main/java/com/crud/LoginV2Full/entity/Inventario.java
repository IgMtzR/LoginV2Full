package com.crud.LoginV2Full.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private  int id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "departamento")
    private String departamento;

    public Inventario() {
    }

    public Inventario(String codigo, String tipo, String descripcion, String estado, String departamento) {
        this.codigo = codigo;
        this.tipo = tipo;
        descripcion = descripcion;
        estado = estado;
        departamento = departamento;
    }
}
