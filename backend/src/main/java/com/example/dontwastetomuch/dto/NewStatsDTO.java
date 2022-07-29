package com.example.dontwastetomuch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewStatsDTO {

    private String gameId;
    private double addedPlaytime;
    private double addedSpentMoneyGamePass;
    private double addedSpentMoneyCoins;
    private double addedSpentMoneyGame;

}
