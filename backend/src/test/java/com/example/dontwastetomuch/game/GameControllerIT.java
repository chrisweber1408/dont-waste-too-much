package com.example.dontwastetomuch.game;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldTestTheController(){

        //addUserGame
        Game game1 = new Game("123", "game1", false);
        ResponseEntity<Void> responseEntity1 = testRestTemplate.postForEntity("/api/game/user", game1, Void.class);
        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //addAdminGame
        Game game2 = new Game("1234", "game2", true);
        ResponseEntity<Void> responseEntity2 = testRestTemplate.postForEntity("/api/game/admin", game2, Void.class);
        Assertions.assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //getAllGames
        ResponseEntity<Game[]> forEntity1 = testRestTemplate.getForEntity("/api/game", Game[].class);
        Assertions.assertThat(forEntity1.getBody()).hasSize(2);

        //getOneById
        ResponseEntity<Game> forEntity2 = testRestTemplate.getForEntity("/api/game/" + game1.getId(), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity2.getBody()).getGameName()).isEqualTo("game1");

        //editGame
        testRestTemplate.exchange("/api/game", HttpMethod.PUT, new HttpEntity<>(game1), Void.class);
        ResponseEntity<Game> forEntity3 = testRestTemplate.getForEntity("/api/game/" + game1.getId(), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity3.getBody()).isApproved()).isEqualTo(true);

    }

}
