package com.dicegamefinal.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuarios")//, uniqueConstraints = { @UniqueConstraint(columnNames = { "userName" })})//usernames uniccos
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//de no esecificarsse genera una exception lazy (no encuentra los roles precargados)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))//de especificarse lo contrario da exception duplicate entry(los usuarios que crea dice estar duplicados)
    private Set<Rol> roles;

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
