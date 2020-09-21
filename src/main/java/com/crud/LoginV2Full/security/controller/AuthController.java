package com.crud.LoginV2Full.security.controller;


import com.crud.LoginV2Full.exception.Msg;
import com.crud.LoginV2Full.security.dto.JwtDto;
import com.crud.LoginV2Full.security.dto.LoginUsuario;
import com.crud.LoginV2Full.security.dto.NuevoUsuario;
import com.crud.LoginV2Full.security.entity.Rol;
import com.crud.LoginV2Full.security.entity.Usuario;
import com.crud.LoginV2Full.security.enums.RolNombre;
import com.crud.LoginV2Full.security.jwt.JwtProvider;
import com.crud.LoginV2Full.security.service.RolService;
import com.crud.LoginV2Full.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    //inyeccion de dependencias para codificar, autenticar, implementar metodos
    //para cada interceccion de peticiones, manejo de roles e implementacion de tokens mediante el provedor jwt
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/create-user")
    public ResponseEntity<?> nuevo(@RequestBody NuevoUsuario nuevoUsuario){
        /*
        * parametros de control en la inyeccion de datpos exernos mediate RequestBody*/
        if(nuevoUsuario.getNombre()==null||nuevoUsuario.getNombre().isEmpty()){
            return new ResponseEntity(new Msg("Campo nombre vacio"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            return new ResponseEntity(new Msg("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(nuevoUsuario.getEmail()==null||nuevoUsuario.getEmail().isEmpty()){
            return new ResponseEntity(new Msg("Campo email vacio"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
            return new ResponseEntity(new Msg("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(nuevoUsuario.getNombreUsuario()==null||nuevoUsuario.getNombreUsuario().isEmpty()){
            return new ResponseEntity(new Msg("Campo Username vacio"), HttpStatus.BAD_REQUEST);
        }
        if(nuevoUsuario.getPassword()==null||nuevoUsuario.getPassword().isEmpty()){
            return new ResponseEntity(new Msg("Campo Password vacio"), HttpStatus.BAD_REQUEST);
        }
        /*
        * si los parametros de seguridad son corroborados  se procede a la creacion de la entidAD USUARIO*/
        Usuario usuario =
                new Usuario(    nuevoUsuario.getNombre(),//captura de parametros
                                nuevoUsuario.getNombreUsuario(),//captura de parametros
                                nuevoUsuario.getEmail(),//captura de parametros
                                passwordEncoder.encode(
                                        nuevoUsuario.getPassword()//encriptacion  del parametro password
                                )
                );
        Set<Rol> roles = new HashSet<>();//se genera una coleccion de tipo rol

        //si se reguiere generar un usuario se le asigna el rol correspondiente
        if(nuevoUsuario.getRoles().contains("user")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());//agregando rol a la coleccion
        }
        //si se reguiere generar un administrador se le asigna el rol correspondiente
        if(nuevoUsuario.getRoles().contains("admin")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());//agregando rol a la coleccion
        }
        //si se reguiere generar un desarrollador se le asigna el rol correspondiente
        if(nuevoUsuario.getRoles().contains("developer")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DEVELOPER).get());//agregando rol a la coleccion
        }

        usuario.setRoles(roles);//pasando coleccion de parametros rol para autenticar a entidad usuario
        usuarioService.save(usuario);//guardando parametros en tabla usuario y tsbla referenciada user_rol

        return new ResponseEntity(new Msg("Usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUsuario loginUsuario){
        //restricciones para datos de entrada desde el RequestBody
        if(loginUsuario.getNombreUsuario()==null||loginUsuario.getNombreUsuario().isEmpty()){
            return new ResponseEntity(new Msg("Campo Username vacio"), HttpStatus.BAD_REQUEST);
        }
        if(loginUsuario.getPassword()==null||loginUsuario.getPassword().isEmpty()){
            return new ResponseEntity(new Msg("Campo Password vacio"), HttpStatus.BAD_REQUEST);
        }

        //estableciendo la autenticacon de los datos resividos mediante authenticationManager
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginUsuario.getNombreUsuario(),
                                loginUsuario.getPassword()));
        //asignando autenticacion
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //llamando la clase JwtProvider para generar el token mediante los parametros asignados a authentication
        String jwt = jwtProvider.generateToken(authentication);
        //generando userDetails apatir de la authentication
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        //se crea un objeto JwtDto que contiene : token, username, y los roles
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        //se envia el archivo JwtDto que contiene los datos requeridos para poder realziar una autenticacion
        //correcta con el usuario y password correctos
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}








