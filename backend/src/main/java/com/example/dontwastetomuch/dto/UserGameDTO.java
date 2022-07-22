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
    private double spentMoney;
    private double playtime;
    private boolean approved;

}
