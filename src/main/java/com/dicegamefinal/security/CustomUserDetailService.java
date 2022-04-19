package com.dicegamefinal.security;

import com.dicegamefinal.model.Rol;
import com.dicegamefinal.model.Usuario;
import com.dicegamefinal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    /*
    este metodo carga o busca a un usuario por su userName
     */
    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        Usuario user = userRepo.findByUserName(userNameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email : " + userNameOrEmail));
        return new User(user.getUserName(), user.getPassword(), mapearRoles(user.getRoles()));//retorna un User de Spring Security, con los parametros requeridos
    }

    /*
    con este metodo se mapean los roles y es necesario para poder genrar un objeto user de spring
     */
    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){//este metodo se usa para mapear los roles
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolName()))//los grandes roles pasan a simple
                .collect(Collectors.toList());
    }
    /*
    He creado manualmente en la bases de datos los users y roles, ver base de datos
     */
}
