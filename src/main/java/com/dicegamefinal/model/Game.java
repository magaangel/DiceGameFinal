package com.dicegamefinal.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "idPlayer")
    private long idPlayer;
    @Column(name = "dateTimeGameRegister")
    private LocalDateTime dateTimeRegister;
    @Column(name = "dice1")
    private int dice1;
    @Column(name = "dice2")
    private int dice2;
    @Column(name = "resultRollDice")
    private int resultRollDice;

    public Game() {
        this.dateTimeRegister = LocalDateTime.now();
    }

    public Game(long idPlayer, int dice1, int dice2, int resultRollDice) {
        this.idPlayer = idPlayer;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.resultRollDice = resultRollDice;
        this.dateTimeRegister = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public int getResultRollDice() {
        return resultRollDice;
    }

    public void setResultRollDice(int resultRollDice) {
        this.resultRollDice = resultRollDice;
    }

}
