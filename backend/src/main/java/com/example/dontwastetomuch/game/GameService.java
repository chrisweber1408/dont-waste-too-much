package com.example.dontwastetomuch.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepo gameRepo;

    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    public void addAGame(Game game) {
        gameRepo.save(game);
    }

    public List<Game> getAllApprovedGamesAndNotApprovedGamesFromUser() {
        return gameRepo.findAll();
    }
}
