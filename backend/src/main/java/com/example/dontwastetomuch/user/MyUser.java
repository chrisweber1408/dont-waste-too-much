package com.example.dontwastetomuch.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String roll = "user";
    private List<GameData> gameData;

}



