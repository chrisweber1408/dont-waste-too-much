package com.example.dontwastetomuch.game;

import lombok.Data;

@Data
public class GameData {

    private String gameId;
    private double playtime;
    private double spentMoneyGamePass;
    private double spentMoneyCoins;
    private double spentMoneyGame;

    public GameData(String gameId) {
        this.gameId = gameId;
    }
}
