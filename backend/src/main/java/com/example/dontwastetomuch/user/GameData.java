package com.example.dontwastetomuch.user;

import lombok.Data;

@Data
public class GameData {

    private String gameId;
    private double playtime;
    private double money;

    public GameData(String gameId) {
        this.gameId = gameId;
    }
}
