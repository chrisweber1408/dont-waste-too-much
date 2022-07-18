package com.example.dontwastetomuch.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "User")
public class MyUser {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String roll = "user";
    private List<GameData> gameData;

}
