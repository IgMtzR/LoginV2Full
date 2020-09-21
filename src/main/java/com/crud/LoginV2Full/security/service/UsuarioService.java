package com.crud.LoginV2Full.security.service;


import com.crud.LoginV2Full.security.entity.Usuario;
import com.crud.LoginV2Full.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    //inyeccion de dependencias
    @Autowired
    UsuarioRepository usuarioRepository;

    //metodo de consulta de una dependencia usuario desde la base de datos
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        //llamando al metodo findByNombreUsuario desde la dependencia inyectada
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    //metodo que comprueba la existencia de una entidad dentro de la database mediante el nombreUsuario
    public boolean existsByNombreUsuario(String nombreUsuario){
        //llamando al metodo existsByNombreUsuario desde la dependencia inyectada
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    //metodo que comprueba la existencia de una entidad dentro de la database mediante el email
    public boolean existsByEmail(String email){
        //llamando al metodo existsByEmail desde la dependencia inyectada
        return usuarioRepository.existsByEmail(email);
    }

    //metodo para guardar una entidad dentro de la database
    public void save(Usuario usuario){
        //llamando al metodo save desde la dependencia inyectada
        usuarioRepository.save(usuario);
    }
}