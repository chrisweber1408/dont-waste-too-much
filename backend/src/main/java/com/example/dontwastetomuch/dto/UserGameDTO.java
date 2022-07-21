package com.example.dontwastetomuch.dto;


import lombok.Data;


@Data
public class UserGameDTO {

    private String username;
    private String gameName;
    private String gameId;
    private double spentMoney;
    private double playtime;
    private boolean approved;

}
