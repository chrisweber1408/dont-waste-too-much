package com.example.dontwastetomuch.game;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "games")
@NoArgsConstructor
public class Game {

    @Id
    private String id;
    private String gameName;
    private double spentMoney;
    private double playtime;

    public Game(String gameName) {
        this.gameName = gameName;
    }
}
