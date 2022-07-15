package com.example.dontwastetomuch.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepo gameRepo;

    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

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
}
