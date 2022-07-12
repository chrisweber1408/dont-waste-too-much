package com.example.dontwastetomuch.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "games")
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    private String id;
    @Indexed(unique = true)
    private String gameName;
    private double spentMoney;
    private double playtime;
    private boolean approved;

    public Game(String gameName, boolean approved) {
        this.gameName = gameName;
        this.approved = approved;
    }

}
