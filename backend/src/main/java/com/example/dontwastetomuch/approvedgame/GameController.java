package com.example.dontwastetomuch.approvedgame;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/user")
    public void addUserGame(@RequestBody Game game){
        gameService.addUserGame(game);
    }

    @PostMapping("/admin")
    public void addAdminGame(@RequestBody Game game){
        gameService.addAdminGame(game);
    }
    
    @GetMapping()
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public Game getOneGame(@PathVariable String gameId){
        return gameService.getOneGame(gameId);
    }

    @PutMapping()
    public void editGame(@RequestBody Game game){
        gameService.editGame(game);
    }
}
