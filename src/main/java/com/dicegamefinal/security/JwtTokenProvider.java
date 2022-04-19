package com.dicegamefinal.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    /*
    en esta clase se generan los tokens, obtener los claims y validaar el token
     */
    @Value("${app.jwt-secret}")//obtiene el valor del application properties
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")//lo mismo
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        /*
        contiene las tres partes del token. Se indica el tiempo de expiracion y que se firma con el algoritmo HS512
         */
        return token;//retorna el token
    }

    public String obtenerUsernameDelJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();//porcion de informacion del cuerpo del token, las propiedades del token
        return claims.getSubject();//creo que es payload
    }

    public boolean validarToken(String token) {//valida que el token este correcto
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);//se valida el token con su llave secreta
            return true;//si es valido retorna true, de lo contrario se añaden las excepciones que siguen
        } catch (SignatureException ex) {
            ex.getStackTrace();
        } catch (MalformedJwtException ex) {
            ex.getCause();
        } catch (ExpiredJwtException ex) {
            ex.getStackTrace();
        } catch (UnsupportedJwtException ex) {
            ex.getStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.getStackTrace();
        }
        return false;
    }
}
