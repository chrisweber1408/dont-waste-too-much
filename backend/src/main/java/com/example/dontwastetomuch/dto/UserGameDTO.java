package com.example.dontwastetomuch.dto;


import lombok.Data;


@Data
public class UserGameDTO {

    private String username;
    private String gameName;
    private double spentMoney;
    private double playtime;

}
