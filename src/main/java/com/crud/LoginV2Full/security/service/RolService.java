package com.crud.LoginV2Full.security.service;

import com.crud.LoginV2Full.security.entity.Rol;
import com.crud.LoginV2Full.security.enums.RolNombre;
import com.crud.LoginV2Full.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional//metodo transaccion permite corregir errores de lectura y escritura en caso de estos realiza un callback
public class RolService {

    //inyeccion de dependencias
    @Autowired
    RolRepository rolRepository;

    //creando un objeto de retorno que contendra parametros correspondientes a rol
    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        //realiza busqueda del rol por medio del nombre de este
        return rolRepository.findByRolNombre(rolNombre);
    }

    //metodo guardar para los roles
    public void save(Rol rol){
        rolRepository.save(rol);
    }
}