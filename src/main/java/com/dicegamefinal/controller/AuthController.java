package com.dicegamefinal.controller;

import com.dicegamefinal.dto.LoginDTO;
import com.dicegamefinal.dto.RegistroDTO;
import com.dicegamefinal.model.Rol;
import com.dicegamefinal.model.Usuario;
import com.dicegamefinal.repository.RolRepo;
import com.dicegamefinal.repository.UserRepo;
import com.dicegamefinal.security.JWTAuthResonseDTO;
import com.dicegamefinal.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController//anotacion controlador
@RequestMapping("/players/auth")//requiere un api/auth es su endpoint
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;//generacion de autenticacion, en la clase SecurityConfig se creo el bean

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RolRepo rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;//se creo el bean en la clase SecurityConfig

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    /*
    con este controlador se inicia sesion, se pasan la credenciales y autenticar que sea cierto, apoyado en el objeto authenticationManager
     */
    @PostMapping("/iniciarSesion")//cuando se quiera hacer una peticion se hace un inicio de sesion
    public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager//se autentica el usuario, sirve para iniciar sesion
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));//obtenemos un username y password autenticado

        SecurityContextHolder.getContext().setAuthentication(authentication);//se obtiene el contexto y se establece la autenticacion

        //obtenemos el token del jwtProvider
        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JWTAuthResonseDTO(token));
    }

    @PostMapping("/registrar")//registro de un usuario
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
        if (userRepo.existsByUserName(registroDTO.getUserName())) {//se asegura que el usuario no exista
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        /*
        de no existir el usuario se crea el usuario, es decir, se establecen los datos del usuario
         */
        Usuario user = new Usuario();
        user.setUserName(registroDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registroDTO.getPassword()));//se codifica la contrasena
        /*
        a continuación se establecen los roles, es decir, el rol del usuario
         */
        Rol roles = rolRepo.findByRolName("ROLE_ADMIN").get();//los roles estan guardados en la base de datos, SE DEBEN GUADAR ROLE_ADMIN, de los contrario genera errores
        user.setRoles(Collections.singleton(roles));//patron singleton, solo se crea un objeto de este rol, este rol puede tener muchos usuarios

        userRepo.save(user);//se guardan los cambios
        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);

    }

}
