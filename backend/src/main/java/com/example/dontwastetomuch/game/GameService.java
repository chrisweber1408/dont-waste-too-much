package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.GameData;
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


    public void addAdminGame(Game game) {
        game.setApproved(true);
        gameRepo.save(game);
    }

    public void addUserGame(Game game) {
        game.setApproved(false);
        gameRepo.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Game getOneGame(String gameId) {
        return gameRepo.findById(gameId).orElseThrow();
    }

    public void editGame(Game game) {
        if (game.isApproved()) {
            game.setApproved(false);
            gameRepo.save(game);
        }else {
            game.setApproved(true);
            gameRepo.save(game);
        }
    }

    public void addMyGame(MyUser myUser, String gameId) {
        GameData gameData = new GameData(gameId);
        myUser.addGameData(gameData);
        myUserRepo.save(myUser);
    }

    public List<GameData> getAllMyGames(MyUser myUser) {
        return myUser.getGameData();
    }
}
