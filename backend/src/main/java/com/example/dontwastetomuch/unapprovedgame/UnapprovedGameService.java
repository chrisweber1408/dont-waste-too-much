package com.example.dontwastetomuch.unapprovedgame;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnapprovedGameService {

    private final UnapprovedGameRepo unapprovedGameRepo;

    public UnapprovedGameService(UnapprovedGameRepo unapprovedGameRepo) {
        this.unapprovedGameRepo = unapprovedGameRepo;
    }

    public void addAUnapprovedGame(UnapprovedGame unapprovedGame) {
        unapprovedGameRepo.save(unapprovedGame);
    }

    public List<UnapprovedGame> getAllUnapprovedGames() {
        return unapprovedGameRepo.findAll();
    }

    public UnapprovedGame getOneUnapprovedGame(String gameId) {
        return unapprovedGameRepo.findById(gameId).orElseThrow();
    }

    public void editUnapprovedGame(UnapprovedGame unapprovedGame) {
        unapprovedGameRepo.save(unapprovedGame);
    }
}
