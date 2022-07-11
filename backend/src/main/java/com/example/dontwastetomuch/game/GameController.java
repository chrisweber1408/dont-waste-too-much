package com.example.dontwastetomuch.game;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public void addAGame(@RequestBody Game game){
        gameService.addAGame(game);
    }

    @GetMapping
    public List<Game> getAllApprovedGamesAndNotApprovedGamesFromUser(){
        return gameService.getAllApprovedGamesAndNotApprovedGamesFromUser();
    }
}
