package com.example.dontwastetomuch.approvedgame;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/approved")
@RequiredArgsConstructor
public class ApprovedGameController {

    private final ApprovedGameService approvedGameService;

    @PostMapping()
    public void addAApprovedGame(@RequestBody ApprovedGame approvedGame){
        approvedGameService.addAApprovedGame(approvedGame);
    }
    
    @GetMapping()
    public List<ApprovedGame> getAllApprovedGames(){
        return approvedGameService.getAllGames();
    }

    @GetMapping("/{gameId}")
    public ApprovedGame getOneGame(@PathVariable String gameId){
        return approvedGameService.getOneGame(gameId);
    }

    @PutMapping()
    public void editApprovedGame(@RequestBody ApprovedGame approvedGame){
        approvedGameService.editGame(approvedGame);
    }
}
