package com.dicegamefinal.security;

import com.dicegamefinal.model.Rol;
import com.dicegamefinal.model.Usuario;
import com.dicegamefinal.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomUserDetailServiceTest {

    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    private Usuario user;
    private Rol rol;
    private User userSpring;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = new Usuario();
        user.setUserName("miUsuario");
        user.setPassword("1234");
        user.setRoles(new HashSet<>());

    }

    @Test
    void loadUserByUsername() {
        when(userRepo.findByUserName(any(String.class))).thenReturn(Optional.of(user));
        assertNotNull(customUserDetailService.loadUserByUsername(user.getUserName()));
    }
}