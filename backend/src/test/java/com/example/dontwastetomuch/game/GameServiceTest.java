package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

class GameServiceTest {

    @Test
    void shouldAddAGame(){
        //given
        Game game = new Game("1", "Fifa22", true);
        MyUser user = new MyUser();
        user.setRoles(List.of("admin"));
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
        Game game1 = new Game("2", "League of Legends", true);
        Game game2 = new Game("3", "FIFA 22", true);
        Game game3 = new Game("4", "Rocket League", true);
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
        Game game = new Game("5","FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        Mockito.when(gameRepo.findById("1337")).thenReturn(Optional.of(game));
        //when
        Game actual = gameService.getOneOfMyGames("1337");
        //then
        Assertions.assertThat(actual).isEqualTo(game);
    }

    @Test
    void shouldGetNoGameWithWrongGameId(){
        //given
        Game game = new Game("6", "FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        Mockito.when(gameRepo.findById("1337")).thenReturn(Optional.of(game));
        //then
        Assertions.assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> gameService.getOneOfMyGames("1336"));
    }

    @Test
    void shouldSwitchGameStatus(){
        //given
        Game gameToEdit = new Game("123","FIFA 22", true);
        Game savedGame = new Game("123","FIFA 22", true);
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        GameData gameData = new GameData("1");
        MyUser user = new MyUser("1", "hans", "123", List.of("admin"), List.of(gameData));
        //when
        Mockito.when(gameRepo.findById("123")).thenReturn(Optional.of(savedGame));
        Mockito.when(gameRepo.save(gameToEdit)).thenReturn(gameToEdit);
        //then
        Assertions.assertThatNoException().isThrownBy(()-> gameService.switchStatus(gameToEdit, user));
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
        double spentMoneyGame = user.getGameData().get(0).getSpentMoneyGame();
        double spentMoneyGamePass = user.getGameData().get(0).getSpentMoneyGamePass();
        double spentMoneyCoins = user.getGameData().get(0).getSpentMoneyCoins();
        double playtime = user.getGameData().get(0).getPlaytime();
        //when
        user.getGameData().get(0).setSpentMoneyGame(5);
        user.getGameData().get(0).setSpentMoneyGamePass(5);
        user.getGameData().get(0).setSpentMoneyCoins(5);
        user.getGameData().get(0).setPlaytime(10);
        double newSpentMoneyGame = user.getGameData().get(0).getSpentMoneyGame();
        double newSpentMoneyGamePass = user.getGameData().get(0).getSpentMoneyGamePass();
        double newSpentMoneyCoins = user.getGameData().get(0).getSpentMoneyCoins();
        double newPlaytime = user.getGameData().get(0).getPlaytime();
        //then
        Mockito.verify(myUserRepo).save(user);
        Assertions.assertThat(newPlaytime).isNotEqualTo(playtime);
        Assertions.assertThat(newSpentMoneyGame).isNotEqualTo(spentMoneyGame);
        Assertions.assertThat(newSpentMoneyGamePass).isNotEqualTo(spentMoneyGamePass);
        Assertions.assertThat(newSpentMoneyCoins).isNotEqualTo(spentMoneyCoins);
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




















