package com.dicegamefinal.service;

import com.dicegamefinal.model.Game;
import com.dicegamefinal.model.Player;

import java.util.List;

public interface IplayerService {

    public Player createPlayer (Player player);

    public Player updatePlayer (Player player);

    public List<Player> findAllPlayer();

    public Player findPlayerByEmail(String email);

    public Player findPlayerByNickName(String nickName);

    public Player findPlayerById(Long idPlayer);

    public Game addGameRollDice(Long idPlayer);

    public List<Game> findAllGamesOfAPlayer(Long idPlayer);

    public boolean deleteGamesOfAPlayer(Long idPlayer);

}
