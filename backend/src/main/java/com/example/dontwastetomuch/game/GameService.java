package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.NewStatsDTO;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo gameRepo;
    private final MyUserRepo myUserRepo;


    public void addGame(Game game, MyUser user) {
        if (gameRepo.findByGameName(game.getGameName()).isPresent()){
            throw new IllegalArgumentException("The game already exists");
        } else if (game.getGameName().isBlank()){
            throw new IllegalStateException("The game name is blank");
        } else {
            game.setApproved(user.getRoles().stream().anyMatch(roles -> roles.contains("admin")));
            gameRepo.save(game);
        }

    }

    public List<Game> getAllGames() {
        if (gameRepo.findAll().isEmpty()){
            throw new NoSuchElementException("List is empty");
        } else {
            return gameRepo.findAll();
        }

    }

    public Game getOneOfMyGames(String gameId) {
        if (gameRepo.findById(gameId).isEmpty()){
            throw new NoSuchElementException("Game not found");
        } else {
            return gameRepo.findById(gameId).orElseThrow();
        }

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
            throw new IllegalArgumentException("No admin!");
        }
    }


    public void addMyGame(MyUser myUser, String gameId){
        GameData gameData = new GameData(gameId);
        if (myUser.getGameData() == null || myUser.getGameData().stream().noneMatch(gameData1 -> gameId.equals(gameData1.getGameId()))){
            myUser.addGameData(gameData);
            myUserRepo.save(myUser);
        } else {
            throw new IllegalArgumentException("Game already added to your list");
        }
    }

    public void removeMyGame(MyUser myUser, String gameId){
        if (myUser.getGameData().stream().anyMatch(game -> gameId.equals(game.getGameId()))){
            GameData data = myUser.getGameData().stream().filter(game -> gameId.equals(game.getGameId())).findAny().orElseThrow();
            myUser.getGameData().remove(data);
            myUserRepo.save(myUser);
        } else {
            throw new IllegalArgumentException("Game not found");
        }


    }

    public List<GameData> getAllMyGames(MyUser myUser) {
        if (myUser.getGameData().isEmpty()){
            throw new NoSuchElementException("Add some games to your list");
        } else {
            return myUser.getGameData();
        }
    }

    public void updateGameStats(MyUser user, NewStatsDTO newStatsDTO) {
        GameData gameData = user.getGameData().stream().filter(game -> newStatsDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow();
        gameData.setPlaytime(gameData.getPlaytime() + newStatsDTO.getAddedPlaytime());
        gameData.setSpentMoneyGame(gameData.getSpentMoneyGame() + newStatsDTO.getAddedSpentMoneyGame());
        gameData.setSpentMoneyCoins(gameData.getSpentMoneyCoins() + newStatsDTO.getAddedSpentMoneyCoins());
        gameData.setSpentMoneyGamePass(gameData.getSpentMoneyGamePass() + newStatsDTO.getAddedSpentMoneyGamePass());
        myUserRepo.save(user);
    }
}






















