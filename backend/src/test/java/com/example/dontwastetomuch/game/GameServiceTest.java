package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;

class GameServiceTest {

    @Test
    void shouldAddAGameAsUser(){
        //given
        Game game = new Game("1", "Fifa22");
        MyUser user = new MyUser();
        user.setRoles(List.of("user"));
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addGame(game, user);
        user.setRoles(List.of("admin"));
        Mockito.when(gameRepo.findById("1")).thenReturn(Optional.of(game));
        Game oneGameToEdit = gameService.getOneGameToEdit("1");
        boolean approved = oneGameToEdit.isApproved();
        //then
        Assertions.assertThat(approved).isEqualTo(false);
        Mockito.verify(gameRepo).save(game);
    }

    @Test
    void shouldAddAGameAsAdmin(){
        //given
        Game game = new Game("1", "Fifa22");
        MyUser user = new MyUser();
        user.setRoles(List.of("admin"));
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addGame(game, user);
        Mockito.when(gameRepo.findById("1")).thenReturn(Optional.of(game));
        Game oneGameToEdit = gameService.getOneGameToEdit("1");
        boolean approved = oneGameToEdit.isApproved();
        //then
        Assertions.assertThat(approved).isEqualTo(true);
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
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gameService.getOneOfMyGames("1336"));
    }


    @Test
    void shouldSwitchGameStatusByAdmin(){
        //given
        Game gameToSwitchStatus = new Game("123","FIFA 22");
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        GameData gameData = new GameData("1");
        MyUser user = new MyUser("1", "hans", "123", List.of("admin"), List.of(gameData));
        //when
        gameService.addGame(gameToSwitchStatus, user);
        Mockito.when(gameRepo.findById("123")).thenReturn(Optional.of(gameToSwitchStatus));
        Mockito.when(gameRepo.save(gameToSwitchStatus)).thenReturn(gameToSwitchStatus);
        gameService.switchStatus("123");
        //then
        Assertions.assertThat(gameToSwitchStatus.isApproved()).isEqualTo(false);
        Assertions.assertThatNoException().isThrownBy(()-> gameService.switchStatus(gameToSwitchStatus.getId()));
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
    void shouldNotAddAGameToMyGamesBecauseAlreadyAdded(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        gameService.addMyGame(user, "123");
        //then
        Mockito.verify(myUserRepo).save(user);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(()-> gameService.addMyGame(user, "123"));

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
    void shouldNotListMyGamesBecauseNoGameAdded(){
        //given
        MyUser user = new MyUser();
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(()-> gameService.getAllMyGames(user));
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

    @Test
    void shouldNotRemoveOneGameFromMyListBecauseNoGameInMyList(){
        //given
        MyUser user = new MyUser();
        //then
        Assertions.assertThatExceptionOfType(NullPointerException.class).isThrownBy(()-> user.getGameData().remove(0));
    }

    @Test
    void shouldDeleteOneGameAsAdmin(){
        //given
        Game game = new Game("1", "Fifa22");
        MyUser user = new MyUser();
        user.setRoles(List.of("admin"));
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        gameService.addGame(game,user);
        //when
        Mockito.when(gameRepo.findById("1")).thenReturn(Optional.of(game));
        gameService.deleteGame("1");
        //then
        Mockito.verify(gameRepo).delete(game);
    }

    @Test
    void shouldGetOneGameToEditAsAdmin(){
        //given
        Game game = new Game("1", "Fifa22");
        MyUser user = new MyUser();
        user.setRoles(List.of("admin"));
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        //when
        Mockito.when(gameRepo.findById("1")).thenReturn(Optional.of(game));
        Game oneGameToEdit = gameService.getOneGameToEdit("1");
        //then
        Assertions.assertThat(oneGameToEdit.getGameName()).isEqualTo("Fifa22");
    }

    @Test
    void shouldEditOneGameAsAdmin(){
        //given
        Game gameToEdit = new Game("123","FIFA 22");
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        MyUserRepo myUserRepo = Mockito.mock(MyUserRepo.class);
        GameService gameService = new GameService(gameRepo, myUserRepo);
        GameData gameData = new GameData("1");
        MyUser user = new MyUser("1", "hans", "123", List.of("admin"), List.of(gameData));
        gameService.addGame(gameToEdit, user);
        //when
        Mockito.when(gameRepo.findById("123")).thenReturn(Optional.of(gameToEdit));
        Mockito.when(gameRepo.save(gameToEdit)).thenReturn(gameToEdit);
        gameToEdit.setGameName("FIFA 23");
        gameService.editOneGame(gameToEdit);
        //then
        Assertions.assertThat(gameToEdit.getGameName()).isEqualTo("FIFA 23");
        Assertions.assertThatNoException().isThrownBy(()-> gameService.switchStatus(gameToEdit.getId()));
    }
}




















