package com.example.dontwastetomuch.unapprovedgame;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/unapproved")
@RequiredArgsConstructor
public class UnapprovedGameController {

    private final UnapprovedGameService approvedGameService;

    @PostMapping()
    public void addAUnapprovedGame(@RequestBody UnapprovedGame unapprovedGame){
        approvedGameService.addAUnapprovedGame(unapprovedGame);
    }

    @GetMapping
    public List<UnapprovedGame> getAllUnapprovedGames(){
        return approvedGameService.getAllUnapprovedGames();
    }

    @GetMapping("/{gameId}")
    public UnapprovedGame getOneUnapprovedGame(@PathVariable String gameId){
        return approvedGameService.getOneUnapprovedGame(gameId);
    }

    @PutMapping
    public void editUnapprovedGame(@RequestBody UnapprovedGame unapprovedGame){
        approvedGameService.editUnapprovedGame(unapprovedGame);
    }
}
