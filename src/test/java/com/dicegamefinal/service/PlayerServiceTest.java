package com.dicegamefinal.service;

import com.dicegamefinal.model.Game;
import com.dicegamefinal.model.Player;
import com.dicegamefinal.repository.IgameRepository;
import com.dicegamefinal.repository.IplayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PlayerServiceTest {

    @Mock//simula los atributos de la clase
    private IplayerRepository playerRepository;
    @Mock
    private IgameRepository gameRepository;
    @InjectMocks//simula un objeto de la clase que se prueba, objeto simulado de una dependencia que no puede ser probada, ejm una bd
    private PlayerService playerService;
    private Player player;//variable necesarias para realizar las pruebas de los metodos
    private Game game;//lo mismo

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);//inicializa los mocks
        /*se inicializan los valores de las variables necesarias para la prueba*/
        player = new Player();
        player.setId(1);
        player.setEmail("email@gmail.com");
        player.setNickName("playerOne");
        player.setGames(new ArrayList<Game>());

        game = new Game();
        game.setId(1);
        game.setIdPlayer(1);
        game.setDice1(2);
        game.setDice2(3);
        game.setResultRollDice(5);
    }

    @Test
    void createPlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);//prueba mock
        assertEquals(playerService.createPlayer(player), player);//prueba injectmocks
    }

    @Test
    void updatePlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);//cuando se usa un objeto Player, retorna un player
        assertEquals(playerService.updatePlayer(player), player);//el metodo retorna el mismo objeto player
    }

    @Test
    void findAllPlayer() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player));//se comprueba que retorna una lista de jugadores
        assertNotNull(playerService.findAllPlayer());//se comprueba que la lista no es nula
    }

    @Test
    void findPlayerByEmail() {
        when(playerRepository.findByEmail(any(String.class))).thenReturn(player);
        assertEquals(playerService.findPlayerByEmail(player.getEmail()), player);
    }

    @Test
    void findPlayerByNickName() {
        when(playerRepository.findByEmail(any(String.class))).thenReturn(player);
        assertEquals(playerService.findPlayerByEmail(player.getNickName()), player);
    }

    @Test
    void findPlayerById() {
        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));
        assertEquals(playerService.findPlayerById(player.getId()), player);
    }

    @Test
    void addGameRollDice() {
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));//se deben probar todos los mocks que contiene el metodo
        assertEquals(playerService.addGameRollDice(player.getId()), game);
    }

    @Test
    void findAllGamesOfAPlayer() {
        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));
        assertEquals(playerService.findAllGamesOfAPlayer(player.getId()), player.getGames());
    }

    @Test
    void deleteGamesOfAPlayer() {
        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));
        assertTrue(playerService.deleteGamesOfAPlayer(player.getId()));
    }

}