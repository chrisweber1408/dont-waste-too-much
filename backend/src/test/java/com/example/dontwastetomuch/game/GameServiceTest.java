package com.example.dontwastetomuch.game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class GameServiceTest {

    @Test
    void shouldAddAGame(){
        //given
        Game game = new Game("Fifa22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        GameService gameService = new GameService(gameRepo);
        //when
        gameService.addAGame(game);
        //then
        Mockito.verify(gameRepo).save(game);
    }

    @Test
    void shouldListAllGames(){
        //given
        Game game1 = new Game("League of Legends", true);
        Game game2 = new Game("FIFA 22", true);
        Game game3 = new Game("World of Warcraft", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        GameService gameService = new GameService(gameRepo);
        gameService.addAGame(game1);
        gameService.addAGame(game2);
        gameService.addAGame(game3);
        //when
        List<Game> allGames = gameService.getAllApprovedGamesAndNotApprovedGamesFromUser();
        //then
        Assertions.assertThat(allGames).hasSize(3);
    }
}