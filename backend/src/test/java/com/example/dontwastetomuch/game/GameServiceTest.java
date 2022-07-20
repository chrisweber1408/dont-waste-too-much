package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.MyUserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class GameServiceTest {

    @Test
    void shouldAddAGame(){
        //given
        Game game = new Game("Fifa22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addAdminGame(game);
        //then
        Mockito.verify(gameRepo).save(game);
    }

    @Test
    void shouldListAllGames(){
        //given
        Game game1 = new Game("League of Legends", true);
        Game game2 = new Game("FIFA 22", true);
        Game game3 = new Game("Rocket League", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        Mockito.when(gameRepo.findAll()).thenReturn(List.of(game1, game2, game3));
        //when
        List<Game> allGames = gameService.getAllGames();
        //then
        Assertions.assertThat(allGames).isEqualTo(List.of(game1, game2, game3));
        Assertions.assertThat(allGames).hasSize(3);
    }

    @Test
    void shouldGetOneGameWithGameId(){
        //given
        Game game = new Game("FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        Mockito.when(gameRepo.findById("1337")).thenReturn(Optional.of(game));
        //when
        Game actual = gameService.getOneGame("1337");
        //then
        Assertions.assertThat(actual).isEqualTo(game);
    }


    @Test
    void shouldEditOneGame(){
        //given
        Game gameToEdit = new Game("123","FIFA 22", true);
        Game savedGame = new Game("123","FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        Mockito.when(gameRepo.findById("123")).thenReturn(Optional.of(savedGame));
        Mockito.when(gameRepo.save(gameToEdit)).thenReturn(gameToEdit);
        //then
        Assertions.assertThatNoException().isThrownBy(()-> gameService.editGame(gameToEdit));
    }

}