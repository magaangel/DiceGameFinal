package com.dicegamefinal.service;

import com.dicegamefinal.model.Game;
import com.dicegamefinal.model.Player;
import com.dicegamefinal.repository.IgameRepository;
import com.dicegamefinal.repository.IplayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PlayerService implements IplayerService{

    @Autowired
    private IplayerRepository playerRepository;
    @Autowired
    private IgameRepository gameRepository;


    @Override
    public Player createPlayer(Player player) {
        try{
            if(player.getEmail().equalsIgnoreCase(playerRepository.findByEmail(player.getEmail()).getEmail())){
                return null;
            }
            if(player.getNickName().equalsIgnoreCase(playerRepository.findByNickName(player.getNickName()).getNickName())){
                return null;
            }
        }catch (Exception e){
            e.getStackTrace();
        }//con lo anterior se evitan repeticiones en email y nickName
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> findAllPlayer() {
        return (List<Player>) playerRepository.findAll();
    }

    @Override
    public Player findPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    @Override
    public Player findPlayerByNickName(String nickName) {
        return playerRepository.findByNickName(nickName);
    }

    @Override
    public Player findPlayerById(Long idPlayer) {
        Optional<Player> op = playerRepository.findById(idPlayer);
        return op.orElse(null);
    }

    @Override
    public Game addGameRollDice(Long idPlayer) {
        int dice1 = new Random().nextInt(6) + 1;//si no se coloca el +1 trae numeros del 0 al 5
        int dice2 = new Random().nextInt(6) + 1;
        int resultRollDices = dice1 + dice2;
        Game game = gameRepository.save(new Game(idPlayer, dice1, dice2, resultRollDices));//se crea un objeto partida (game)
        Player player = this.findPlayerById(idPlayer);//se busca el jugador por id
        player.getGames().add(game);//se agrega la partida a la lista de games del jugador
        player.setTotalGames(player.getGames().size());//actualizamos el contador de total de partidas, el cual sera el size de games
        //this.updatePlayer(player);//actualiza games
        if(game.getResultRollDice() == 7){
            player.setSuccess(player.getSuccess()+1);
        }else{
            player.setFail(player.getFail()+1);
        }
        this.updatePlayer(player);//actualizamos los atributos games, totalGames, success y fail
        if(player.getTotalGames() != 0){
            player.setPercentSuccess(((double)player.getSuccess() / (double)player.getTotalGames()) * 100);//al ser long se deben castear a double, ya que el resultado esperado es double
            player.setPercentFail(((double)player.getFail() / (double)player.getTotalGames()) * 100);//si no se castea lanza una advertencia: integer division in floating point context y los resultados de la division son inexactos
        }
        this.updatePlayer(player);//se actualizan(guardar cambios) los valores de player (%success y %fail)
        int index = player.getGames().indexOf(game);
        return player.getGames().get(index);//retorna la partida agregada al atributo games de player
    }

    @Override
    public List<Game> findAllGamesOfAPlayer(Long idPlayer) {
        Player player = this.findPlayerById(idPlayer);
        return player.getGames();
    }

    @Override
    public boolean deleteGamesOfAPlayer(Long idPlayer) {
        List<Game> gameList = this.findAllGamesOfAPlayer(idPlayer);
        gameList.removeAll(gameList);
        if(gameList.isEmpty()){
            Player player = this.findPlayerById(idPlayer);
            player.setSuccess(0);//se vacian todos los atributos relacionados con games
            player.setFail(0);
            player.setTotalGames(0);
            player.setPercentSuccess(0.0);
            player.setPercentFail(0.0);
            this.updatePlayer(player);//se guardan(actualizan) los valores de los atributos luego de vaciar la lista de games
            return true;
        }
        return false;
    }
}
