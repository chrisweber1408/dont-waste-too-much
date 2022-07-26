package com.example.dontwastetomuch.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGameDTO {

    private String username;
    private String gameName;
    private String gameId;
    private double playtime;
    private double spentMoneyGamePass;
    private double spentMoneyCoins;
    private double spentMoneyGame;
    private boolean approved;

}
