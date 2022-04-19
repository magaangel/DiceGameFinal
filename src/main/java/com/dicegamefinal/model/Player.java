package com.dicegamefinal.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 250, unique = true, name = "email")
    private String email;
    @Column(length = 250, unique = true, name = "nickName")//el varchar debe tener una longitud <= 250 para que funcione unique
    private String nickName;
    @Column(name = "dateRegister")
    private LocalDate dateRegister;
    @Column(name = "games")
    @ElementCollection
    private List<Game> games;
    @Column(name = "success")
    private long success;
    @Column(name = "fail")
    private long fail;
    @Column(name = "totalGame")
    private long totalGames;
    @Column(name = "percentSuccess")
    private double percentSuccess;
    @Column(name = "percentFail")
    private double percentFail;

    public Player() {
        this.dateRegister = LocalDate.now();
    }

    public Player(String email, String nickName) {
        this.email = email.toLowerCase();
        this.nickName = nickName;
        this.dateRegister = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDate getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFail() {
        return fail;
    }

    public void setFail(long fail) {
        this.fail = fail;
    }

    public long getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(long totalGames) {
        this.totalGames = totalGames;
    }

    public double getPercentSuccess() {
        return percentSuccess;
    }

    public void setPercentSuccess(double percentSuccess) {
        this.percentSuccess = percentSuccess;
    }

    public double getPercentFail() {
        return percentFail;
    }

    public void setPercentFail(double percentFail) {
        this.percentFail = percentFail;
    }
}
