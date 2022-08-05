package com.example.dontwastetomuch.game;

import com.example.dontwastetomuch.dto.CommunityStatsDTO;
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
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException | IllegalStateException e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames(){
        try {
            return ResponseEntity.ok(gameService.getAllGames());
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Void> switchGameStatus(@PathVariable String gameId){
        try {
            gameService.switchStatus(gameId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteOneGame(@PathVariable String gameId){
        try{
            gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/myGames/{gameId}")
    public ResponseEntity<Void> putToMyGames(@PathVariable String gameId, Principal principal){
        try {
            MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
            gameService.addMyGame(myUser, gameId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/myGames/{gameId}")
    public ResponseEntity<Void> removeGameFromMyList(@PathVariable String gameId, Principal principal){
        try {
            MyUser myUser = myUserRepo.findById(principal.getName()).orElseThrow();
            gameService.removeMyGame(myUser, gameId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/myGames/update")
    public ResponseEntity<Void> updateGameStats(@RequestBody NewStatsDTO newStatsDTO, Principal principal){
        try{
            MyUser user = myUserRepo.findById(principal.getName()).orElseThrow();
            gameService.updateGameStats(user, newStatsDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/communityGame/{gameId}")
    public ResponseEntity<CommunityStatsDTO> getOneCommunityGame(@PathVariable String gameId){
        try{
            return ResponseEntity.ok(gameService.getOneCommunityGame(gameId));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/edit/{gameId}")
    public ResponseEntity<Game> getOneGameToEdit(@PathVariable String gameId){
        try{
            return ResponseEntity.ok(gameService.getOneGameToEdit(gameId));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editOneGame(@RequestBody Game game){
        try {
            gameService.editOneGame(game);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
