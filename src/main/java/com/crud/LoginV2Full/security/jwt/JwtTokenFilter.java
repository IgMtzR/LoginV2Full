package com.crud.LoginV2Full.security.jwt;

import com.crud.LoginV2Full.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    //estableciendo u punto de entrada para el cliente
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    //inyeccion de dependencias
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        //capturando exceptions
        try {
            String token = getToken(req);
            //si la condicion no se cumple se salda directamente a la emicion de un error
            if(token != null && jwtProvider.validateToken(token)){

                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                //Si realiza la autenticación mediante el empleo de contraseña y nombre usuario,
                //se crea un objeto UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            logger.error("Fallo en el método doFilter " + e.getMessage());
        }
        filterChain.doFilter(req, res);
    }

    //se intercepta el token a partir de una cabecera enviada por el
    // clinete la cual contendra una Authorization y un Bearer con el token
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}