package com.example.dontwastetomuch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatsDTO {

    private String gameName;
    private double totalPlaytime;
    private double totalSpentMoneyGame;
    private double totalSpentMoneyCoins;
    private double totalSpentMoneyGamePass;
    private double averagePlaytime;
    private double averageSpentMoneyGame;
    private double averageSpentMoneyCoins;
    private double averageSpentMoneyGamePass;

}
