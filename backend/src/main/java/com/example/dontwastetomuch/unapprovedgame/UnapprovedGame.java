package com.example.dontwastetomuch.unapprovedgame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "unapprovedGames")
@NoArgsConstructor
@AllArgsConstructor
public class UnapprovedGame {

    @Id
    private String id;
    private String gameName;
    private double spentMoney;
    private double playtime;
    private boolean approved;

}
