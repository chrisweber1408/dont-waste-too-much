package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

class GameServiceTest {

    @Test
    void shouldAddAGame(){
        //given
        Game game = new Game("Fifa22", true);
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addGame(game, user);
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
    void shouldGetNoGameWithWrongGameId(){
        //given
        Game game = new Game("FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        Mockito.when(gameRepo.findById("1337")).thenReturn(Optional.of(game));
        //then
        Assertions.assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> gameService.getOneGame("1336"));
    }

    @Test
    void shouldSwitchGameStatus(){
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
        Assertions.assertThatNoException().isThrownBy(()-> gameService.switchStatus(gameToEdit));
    }

    @Test
    void shouldAddAGameToMyGames(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addMyGame(user, "123");
        //then
        Mockito.verify(myUserRepo).save(user);
    }

    @Test
    void shouldListMyGames(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        gameService.addMyGame(user, "1");
        gameService.addMyGame(user, "2");
        gameService.addMyGame(user, "3");
        //when
        List<GameData> allMyGames = gameService.getAllMyGames(user);
        //then
        Assertions.assertThat(allMyGames).hasSize(3);
    }

    @Test
    void shouldAddPlaytimeAndAddSpentMoneyToMyGame(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        gameService.addMyGame(user, "1");
        double money = user.getGameData().get(0).getMoney();
        double playtime = user.getGameData().get(0).getPlaytime();
        //when
        user.getGameData().get(0).setMoney(5);
        user.getGameData().get(0).setPlaytime(10);
        double newMoney = user.getGameData().get(0).getMoney();
        double newPlaytime = user.getGameData().get(0).getPlaytime();
        //then
        Mockito.verify(myUserRepo).save(user);
        Assertions.assertThat(newPlaytime).isNotEqualTo(playtime);
        Assertions.assertThat(newMoney).isNotEqualTo(money);
    }

    @Test
    void shouldRemoveOneGameFromMyList(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        gameService.addMyGame(user, "1");
        gameService.addMyGame(user, "2");
        gameService.addMyGame(user, "3");
        //when
        user.getGameData().remove(2);
        //then
        Assertions.assertThat(gameService.getAllMyGames(user)).hasSize(2);
    }
}




















