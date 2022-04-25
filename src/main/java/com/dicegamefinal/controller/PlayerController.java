package com.dicegamefinal.controller;

import com.dicegamefinal.model.Game;
import com.dicegamefinal.model.Player;
import com.dicegamefinal.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PreAuthorize("hasRole('ADMIN')")
    //@RolesAllowed("ROL_ADMIN")
    @PostMapping("/players")//crear jugador
    public ResponseEntity createPlayer(@RequestBody Player player){//retorna un response entity
        if(playerService != null){//se asegura que hay registros
            try{//evitar la generacion de un nullpointerexcep
                if(player.getEmail().equalsIgnoreCase(playerService.findPlayerByEmail(player.getEmail()).getEmail())){//condicion de igualdad
                    String msg = "Email asociado, por favor indique otro email";
                    return ResponseEntity.badRequest().body(msg);//visualiza el mensaje
                }
                if(player.getNickName().equalsIgnoreCase(playerService.findPlayerByNickName(player.getNickName()).getNickName())){
                    String msg = "NickName asociado, por favor indique otro NickName";
                    return ResponseEntity.badRequest().body(msg);
                }
            }catch (NullPointerException ex){
                ex.getMessage();
            }

        }
        return new ResponseEntity<Player>(playerService.createPlayer(player), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/players")//actualizar jugador
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player){
        Player p1 = playerService.findPlayerById(player.getId());
        if(p1 != null){
            playerService.updatePlayer(p1);
            return new ResponseEntity<Player>(p1, HttpStatus.OK);
        }
        return new ResponseEntity<Player>(player, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/players/{id}/games")//jugador tira los dados
    public ResponseEntity<Game> playerRollDices(@PathVariable(name = "id") Long idPlayer){
        Player player = playerService.findPlayerById(idPlayer);
        if(player != null) {
            Game m1 = playerService.addGameRollDice(idPlayer);
            return new ResponseEntity<Game>(m1, HttpStatus.OK);
        }
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/players/{id}/games")//eliminar partidas de un jugador
    public ResponseEntity<Boolean> deleteGamesOfAPlayer(@PathVariable("id") Long idPlayer){
        Boolean delete = playerService.deleteGamesOfAPlayer(idPlayer);//retorna un boolean, true si ha sido eliminado games
        playerService.updatePlayer(playerService.findPlayerById(idPlayer));//actualizar para guardar los cambios, es decir, el atributo games vacio
        return new ResponseEntity<Boolean>(delete, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/players/{id}")//lista de partidas de un jugador
    public ResponseEntity<List<Game>> getAllGamesOfAPlayer(@PathVariable("id") Long idplayer){
        Player player = playerService.findPlayerById(idplayer);
        if(player != null){
            return new ResponseEntity<List<Game>>(playerService.findAllGamesOfAPlayer(idplayer), HttpStatus.OK);
        }
        return new ResponseEntity<List<Game>>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/players/ranking")//listado de jugadores con porcentajes
    public ResponseEntity<List<Player>> getListPlayerAndPercentSuccess(){
        List<Player> playerList = playerService.findAllPlayer();
        if(playerList != null){
            return new ResponseEntity<List<Player>>(playerList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/players/ranking/winner")//listado ordenado de mejor a peor porcentaje de victorias
    public ResponseEntity<List<Player>> getListPlayerAndPercentSuccessSorted(){//clase que cree para retornar los valores que me interesan de player y sus porcentajes
        List<Player> playerList = playerService.findAllPlayer();
        if(playerList != null){
            List<Player> sortedList = playerList.stream()//se debe asignar la lista ordenada a una nueva lista, de lo contrario no lo ordena
                    .sorted(Comparator.comparingDouble(Player::getPercentSuccess).reversed())//ordenar la lista de mayor porcentaje de victorias al menor(reversed para descendente)
                    .collect(Collectors.toList());//odenar con lambda y Stream
            return new ResponseEntity<List<Player>>(sortedList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/players/ranking/loser")//listado ordenado de peor a mejor % de victorias
    public ResponseEntity<List<Player>> getListPlayerAndPercentFailSorted(){
        List<Player> playerList = playerService.findAllPlayer();
        if(playerList != null){
            List<Player> sortedList = playerList.stream()//se debe asignar la lista ordenada a una nueva lista, de lo contrario no lo ordena
                    .sorted(Comparator.comparingDouble(Player::getPercentSuccess))//ordenar la lista de menor porcentaje de victorias al mayor (ascendente)
                    .collect(Collectors.toList());//odenar con lambda y Stream
            return new ResponseEntity<List<Player>>(sortedList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
    }
}
