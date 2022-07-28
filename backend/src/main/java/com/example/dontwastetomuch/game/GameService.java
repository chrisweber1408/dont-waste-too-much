package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.NewStatsDTO;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo gameRepo;
    private final MyUserRepo myUserRepo;


    public void addGame(Game game, MyUser user) {
        game.setApproved(user.getRoles().stream().anyMatch(roles -> roles.contains("admin")));
        gameRepo.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Game getOneOfMyGames(String gameId) {
        return gameRepo.findById(gameId).orElseThrow();
    }


    public void switchStatus(Game game, MyUser user) {
        if (!user.getRoles().stream().anyMatch(roles -> roles.contains("admin"))){
            throw new IllegalArgumentException("No admin logged in");
        } else {
            if (game.isApproved()) {
                game.setApproved(false);
                gameRepo.save(game);
            }else {
                game.setApproved(true);
                gameRepo.save(game);
            }
        }


    }

    public void deleteGame(MyUser myUser, String gameId){
        Game game = gameRepo.findById(gameId).orElseThrow();
        if (myUser.getRoles().stream().anyMatch(roles -> roles.contains("admin"))){
            gameRepo.delete(game);
        } else {
            throw new IllegalStateException("No admin!");
        }
    }


    public void addMyGame(MyUser myUser, String gameId){
        GameData gameData = new GameData(gameId);
        if (myUser.getGameData() == null || myUser.getGameData().stream().noneMatch(gameData1 -> gameId.equals(gameData1.getGameId()))){
            myUser.addGameData(gameData);
            myUserRepo.save(myUser);
        } else {
            throw new IllegalStateException("Game already added to your list");
        }
    }

    public void removeMyGame(MyUser myUser, String gameId){
        GameData data = myUser.getGameData().stream().filter(game -> gameId.equals(game.getGameId())).findAny().orElseThrow();
        myUser.getGameData().remove(data);
        myUserRepo.save(myUser);
    }

    public List<GameData> getAllMyGames(MyUser myUser) {
        return myUser.getGameData();
    }

    public void updateGameStats(MyUser user, NewStatsDTO newStatsDTO) {
        double playtime = user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().getPlaytime();
        double spentMoneyGame = user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().getSpentMoneyGame();
        double spentMoneyCoins = user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().getSpentMoneyCoins();
        double spentMoneyGamePass = user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().getSpentMoneyGamePass();
        double newPlaytime = playtime + newStatsDTO.getAddedPlaytime();
        double newSpentMoneyGame = spentMoneyGame + newStatsDTO.getAddedSpentMoneyGame();
        double newSpentMoneyCoins = spentMoneyCoins + newStatsDTO.getAddedSpentMoneyCoins();
        double newSpentMoneyGamePass = spentMoneyGamePass + newStatsDTO.getAddedSpentMoneyGamePass();
        user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setPlaytime(newPlaytime);
        user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyGame(newSpentMoneyGame);
        user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyCoins(newSpentMoneyCoins);
        user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyGamePass(newSpentMoneyGamePass);
        myUserRepo.save(user);
    }
}






















