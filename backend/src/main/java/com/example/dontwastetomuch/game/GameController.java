package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.user.GameData;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final MyUserRepo myUserRepo;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserGame(@RequestBody Game game){
        gameService.addUserGame(game);
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
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

    @PutMapping("/{gameId}")
    public void editGame(@PathVariable String gameId){
        Game game = getOneGame(gameId);
        gameService.editGame(game);
    }

    @PutMapping("/myGames/{gameId}")
    public void addMyGame(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.addMyGame(myUser, gameId);
    }

    @GetMapping("/myGames")
    public List<GameData> getAllMyGames(Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        return gameService.getAllMyGames(myUser);
    }

}
