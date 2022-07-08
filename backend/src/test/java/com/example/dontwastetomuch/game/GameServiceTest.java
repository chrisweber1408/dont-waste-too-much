package com.example.dontwastetomuch.game;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameServiceTest {

    @Test
    void shouldAddAGame(){
        Game game = new Game("Fifa22");
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        GameService gameService = new GameService(gameRepo);
        //when
        gameService.addAGame(game);
        //then
        Mockito.verify(gameRepo).save(game);

    }
}