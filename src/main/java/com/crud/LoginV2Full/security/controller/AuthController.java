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

        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if(nuevoUsuario.getRoles().contains("user")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        }
        if(nuevoUsuario.getRoles().contains("admin")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        if(nuevoUsuario.getRoles().contains("developer")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DEVELOPER).get());
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Msg("Usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUsuario loginUsuario){
        if(loginUsuario.getNombreUsuario()==null||loginUsuario.getNombreUsuario().isEmpty()){
            return new ResponseEntity(new Msg("Campo Username vacio"), HttpStatus.BAD_REQUEST);
        }
        if(loginUsuario.getPassword()==null||loginUsuario.getPassword().isEmpty()){
            return new ResponseEntity(new Msg("Campo Password vacio"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
