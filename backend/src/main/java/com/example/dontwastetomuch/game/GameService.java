package com.example.dontwastetomuch.game;

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

    public Game getOneGame(String gameId) {
        return gameRepo.findById(gameId).orElseThrow();
    }


    public void switchStatus(Game game) {
        if (game.isApproved()) {
            game.setApproved(false);
            gameRepo.save(game);
        }else {
            game.setApproved(true);
            gameRepo.save(game);
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

    public void updateGameStats(MyUser user) {
        myUserRepo.save(user);
    }
}






















