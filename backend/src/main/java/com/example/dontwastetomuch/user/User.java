package com.example.dontwastetomuch.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private List<GameData> gameData;

}
