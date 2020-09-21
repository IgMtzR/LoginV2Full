package com.crud.LoginV2Full.security.jwt;

import com.crud.LoginV2Full.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
//se establece que se trata de un componente
@Component
//clase encargada de la generacion y validacion de tokens
public class JwtProvider {
    //estableciendo punto de entrada para e usuario
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    //valores nesesarios para generar el cifrado, se encuentran predeterinados dentro de application.propierties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    //generacion de token mediante ls parametros authentication
    public String generateToken(Authentication authentication){
        //inicializando el usuarioPrincipal mediante su instanciacion
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        //secrea un objeto jwts al que se le asignan prametros como son:
        // username, fecha de inicio, fecha de expirado, y una clave generada mediante "secret"
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    //validacion de los tokens que son resividos con cada acceso al microservicio
    public boolean validateToken(String token){
        //capturado de execptions
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalArgumentException e){
            logger.error("token vacío");
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;
    }
}