package com.example.dontwastetomuch.approvedgame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "approvedGames")
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedGame {

    @Id
    private String id;
    @Indexed(unique = true)
    private String gameName;
    private double spentMoney;
    private double playtime;
    private boolean approved;

    public ApprovedGame(String gameName, boolean approved) {
        this.gameName = gameName;
        this.approved = approved;
    }

}
