package com.example.dontwastetomuch.game;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public void addAGame(@RequestBody Game game){
        gameService.addAGame(game);
    }

}
