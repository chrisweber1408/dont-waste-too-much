package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.UserGameDTO;
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
    public UserGameDTO getOneGame(@PathVariable String gameId, Principal principal){
        MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
        UserGameDTO userGameDTO = new UserGameDTO();
        userGameDTO.setUsername(myUser.getUsername());
        userGameDTO.setGameName(gameService.getOneGame(gameId).getGameName());
        userGameDTO.setPlaytime(myUser.getGameData().stream().filter(gameData ->  gameId.equals(gameData.getGameId())).findAny().orElseThrow().getPlaytime());
        userGameDTO.setSpentMoney(myUser.getGameData().stream().filter(gameData -> gameId.equals(gameData.getGameId())).findAny().orElseThrow().getMoney());
        userGameDTO.setGameId(gameId);
        userGameDTO.setApproved(gameService.getOneGame(gameId).isApproved());
        return userGameDTO;
    }

    @PutMapping("/{gameId}")
    public void switchGameStatus(@PathVariable String gameId, Principal principal){
        UserGameDTO userGameDTO = getOneGame(gameId, principal);
        Game game1 = new Game();
        game1.setGameName(userGameDTO.getGameName());
        game1.setApproved(userGameDTO.isApproved());
        game1.setId(gameId);
        gameService.switchStatus(game1);
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
                    userGameDTO.setGameName(gameService.getOneGame(gameData.getGameId()).getGameName());
                    userGameDTO.setPlaytime(gameData.getPlaytime());
                    userGameDTO.setSpentMoney(gameData.getMoney());
                    userGameDTO.setGameId(gameService.getOneGame(gameData.getGameId()).getId());
                    return userGameDTO;
                }).toList();
    }

    @PutMapping("/myGames/update")
    public void updateGameStats(@RequestBody UserGameDTO userGameDTO, Principal principal){
        MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setMoney(userGameDTO.getSpentMoney());
        user.getGameData().stream().filter(game -> userGameDTO.getGameId().equals(game.getGameId())).findAny().orElseThrow().setPlaytime(userGameDTO.getPlaytime());
        gameService.updateGameStats(user);
    }
}
