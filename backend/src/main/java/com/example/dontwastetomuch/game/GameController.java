package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.NewStatsDTO;
import com.example.dontwastetomuch.dto.UserGameDTO;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final MyUserRepo myUserRepo;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addGame(@RequestBody Game game, Principal principal){
        try {
            MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
            gameService.addGame(game, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException | IllegalStateException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames(){
        try {
            return ResponseEntity.ok(gameService.getAllGames());
        } catch (NoSuchElementException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{gameId}")
    public ResponseEntity<UserGameDTO> getOneOfMyGames(@PathVariable String gameId, Principal principal){
        try{
            MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
            UserGameDTO userGameDTO = new UserGameDTO();
            userGameDTO.setUsername(myUser.getUsername());
            userGameDTO.setGameName(gameService.getOneOfMyGames(gameId).getGameName());
            GameData gameData1 = myUser.getGameData().stream().filter(gameData -> gameId.equals(gameData.getGameId())).findAny().orElseThrow();
            userGameDTO.setPlaytime(gameData1.getPlaytime());
            userGameDTO.setSpentMoneyGame(gameData1.getSpentMoneyGame());
            userGameDTO.setSpentMoneyCoins(gameData1.getSpentMoneyCoins());
            userGameDTO.setSpentMoneyGamePass(gameData1.getSpentMoneyGamePass());
            userGameDTO.setGameId(gameId);
            userGameDTO.setApproved(gameService.getOneOfMyGames(gameId).isApproved());
            return ResponseEntity.ok(userGameDTO);
        } catch (NoSuchElementException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Void> switchGameStatus(@PathVariable String gameId, Principal principal){
        try {
            MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
            ResponseEntity<UserGameDTO> userGameDTO = getOneOfMyGames(gameId, principal);
            Game game1 = new Game();
            game1.setGameName(userGameDTO.getBody().getGameName());
            game1.setApproved(userGameDTO.getBody().isApproved());
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
    public ResponseEntity<Void> removeGameFromMyList(@PathVariable String gameId, Principal principal){
        try {
            MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
            gameService.removeMyGame(myUser, gameId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @GetMapping("/myGames")
    public ResponseEntity<List<UserGameDTO>> fetchAllMyGames(Principal principal){
        try{
            MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
            return ResponseEntity.ok(gameService.getAllMyGames(myUser).stream()
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
                    }).toList());
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/myGames/update")
    public void updateGameStats(@RequestBody NewStatsDTO newStatsDTO, Principal principal){
        MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
        gameService.updateGameStats(user, newStatsDTO);
    }
}
