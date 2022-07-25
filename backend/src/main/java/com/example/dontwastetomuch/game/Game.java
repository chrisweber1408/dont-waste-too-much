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
    private boolean approved;

    public Game(String id, String gameName) {
        this.id = id;
        this.gameName = gameName;
    }

    public Game(String gameName){
        this.gameName = gameName;
    }

}
