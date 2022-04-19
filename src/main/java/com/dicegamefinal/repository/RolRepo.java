package com.dicegamefinal.repository;

import com.dicegamefinal.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolRepo extends JpaRepository<Rol, Integer> {

    public Optional<Rol> findByRolName(String rolName);

}
