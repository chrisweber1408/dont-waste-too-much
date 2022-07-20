package com.example.dontwastetomuch.dto;

import com.example.dontwastetomuch.user.GameData;
import lombok.Data;


@Data
public class UserGameDTO {

    private String username;
    private String gameName;
    private GameData gameData;

}
