package com.dicegamefinal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //obtenemos el token de la solicitud HTTP
        String token = obtenerJWTdeLaSolicitud(request);//este metodo lo definimos en esta clase

        //validamos el token
        if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {//si tiene el texto token y al usar el metodo validaar token to-do es correcto
            //obtenemos el username del token
            String userName = jwtTokenProvider.obtenerUsernameDelJWT(token);

            //cargamos el usuario asociado al token
            CustomUserDetailService customUserDetailsService;
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);//metodo que sobreescribimos en customUserDetailService
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//se pasa la peticion

            //establecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);//se obtienen el contexto y se establece la autenticacion
        }
        filterChain.doFilter(request, response);//para validar el filtro se pasa el request y el response
    }

    //Bearer token de acceso, bearer es un formato que nos permite la autorizacion de un usuario
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");//se obtiene de la cabecera de Authorization del Postman
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {//si tiene el texto bearertoken y comienza con bearer
            return bearerToken.substring(7,bearerToken.length());//se recorta y solo se obtiene el bearer mas un espacio
        }
        return null;
    }


}
