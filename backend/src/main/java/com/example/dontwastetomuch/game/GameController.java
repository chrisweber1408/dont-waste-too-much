package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.UserGameDTO;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final MyUserRepo myUserRepo;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addGame(@RequestBody Game game, Principal principal){
        MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.addGame(game, user);
    }
    
    @GetMapping()
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public UserGameDTO getOneOfMyGames(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        UserGameDTO userGameDTO = new UserGameDTO();
        userGameDTO.setUsername(myUser.getUsername());
        userGameDTO.setGameName(gameService.getOneOfMyGames(gameId).getGameName());
        userGameDTO.setPlaytime(myUser.getGameData().stream().filter(gameData ->  gameId.equals(gameData.getGameId())).findAny().orElseThrow().getPlaytime());
        userGameDTO.setSpentMoneyGame(myUser.getGameData().stream().filter(gameData -> gameId.equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyGame());
        userGameDTO.setSpentMoneyCoins(myUser.getGameData().stream().filter(gameData -> gameId.equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyCoins());
        userGameDTO.setSpentMoneyGamePass(myUser.getGameData().stream().filter(gameData -> gameId.equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyGamePass());
        userGameDTO.setGameId(gameId);
        userGameDTO.setApproved(gameService.getOneOfMyGames(gameId).isApproved());
        return userGameDTO;
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Void> switchGameStatus(@PathVariable String gameId, Principal principal){
        try {
            MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
            UserGameDTO userGameDTO = getOneOfMyGames(gameId, principal);
            Game game1 = new Game();
            game1.setGameName(userGameDTO.getGameName());
            game1.setApproved(userGameDTO.isApproved());
            game1.setId(gameId);
            gameService.switchStatus(game1, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{gameId}")
    public void deleteOneGame(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.deleteGame(myUser, gameId);
    }

    @PutMapping("/myGames/{gameId}")
    public void putToMyGames(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.addMyGame(myUser, gameId);
    }

    @DeleteMapping("/myGames/{gameId}")
    public void removeGameFromMyList(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.removeMyGame(myUser, gameId);
    }

    @GetMapping("/myGames")
    public List<UserGameDTO> fetchAllMyGames(Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        return gameService.getAllMyGames(myUser).stream()
                .map(gameData -> {
                    UserGameDTO userGameDTO = new UserGameDTO();
                    userGameDTO.setUsername(myUser.getUsername());
                    userGameDTO.setGameName(gameService.getOneOfMyGames(gameData.getGameId()).getGameName());
                    userGameDTO.setPlaytime(gameData.getPlaytime());
                    userGameDTO.setSpentMoneyGamePass(gameData.getSpentMoneyGamePass());
                    userGameDTO.setSpentMoneyGame(gameData.getSpentMoneyGame());
                    userGameDTO.setSpentMoneyCoins(gameData.getSpentMoneyCoins());
                    userGameDTO.setGameId(gameService.getOneOfMyGames(gameData.getGameId()).getId());
                    return userGameDTO;
                }).toList();
    }

    @PutMapping("/myGames/update")
    public void updateGameStats(@RequestBody UserGameDTO userGameDTO, Principal principal){
        MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyGame(userGameDTO.getSpentMoneyGame());
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyGamePass(userGameDTO.getSpentMoneyGamePass());
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setSpentMoneyCoins(userGameDTO.getSpentMoneyCoins());
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setPlaytime(userGameDTO.getPlaytime());
        gameService.updateGameStats(user);
    }
}
