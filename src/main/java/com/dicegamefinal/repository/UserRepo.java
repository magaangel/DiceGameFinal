package com.dicegamefinal.repository;

import com.dicegamefinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByUserName(String userName);

    public Boolean existsByUserName(String userName);


}
