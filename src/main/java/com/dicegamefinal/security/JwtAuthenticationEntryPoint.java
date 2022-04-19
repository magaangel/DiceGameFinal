package com.dicegamefinal.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component//es un componente
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
    Esta clase va a servir para poder manejar los errores de un usuario no autorizado, es decir, nos sirve para indicar que un
    usuario no esta autorizado
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        /*
        envia el error de UNAUTHORIZED y la excepcion de autenticacion
         */
    }
}
