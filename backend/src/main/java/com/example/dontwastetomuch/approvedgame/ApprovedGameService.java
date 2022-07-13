package com.example.dontwastetomuch.approvedgame;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovedGameService {

    private final ApprovedGameRepo approvedGameRepo;

    public ApprovedGameService(ApprovedGameRepo approvedGameRepo) {
        this.approvedGameRepo = approvedGameRepo;
    }

    public void addAApprovedGame(ApprovedGame approvedGame) {
        approvedGameRepo.save(approvedGame);
    }

    public List<ApprovedGame> getAllGames() {
        return approvedGameRepo.findAll();
    }

    public ApprovedGame getOneGame(String gameId) {
        return approvedGameRepo.findById(gameId).orElseThrow();
    }

    public void editGame(ApprovedGame approvedGame) {
        approvedGameRepo.save(approvedGame);
    }
}
