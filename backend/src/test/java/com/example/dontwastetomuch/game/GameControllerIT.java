package com.example.dontwastetomuch.game;


import com.example.dontwastetomuch.user.LoginData;
import com.example.dontwastetomuch.user.LoginResponse;
import com.example.dontwastetomuch.user.MyUser;
import com.example.dontwastetomuch.user.MyUserCreationData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldTestTheController(){

        //addUser
        MyUserCreationData user1 = new MyUserCreationData();
        user1.setUsername("Hans");
        user1.setPassword("123");
        user1.setPasswordRepeat("123");
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/api/users", user1, Void.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //loginUser
        LoginData loginData = new LoginData();
        loginData.setUsername("Hans");
        loginData.setPassword("123");
        ResponseEntity<LoginResponse> loginResponseResponseEntity = testRestTemplate.postForEntity("/api/login", loginData, LoginResponse.class);
        Assertions.assertThat(loginResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(loginResponseResponseEntity.getBody()).getJwt()).isNotBlank();

        //addUserGame
        String token = loginResponseResponseEntity.getBody().getJwt();
        Game game1 = new Game("123", "game1", false);
        ResponseEntity<Void> responseEntity1 = testRestTemplate.exchange("/api/game/user", HttpMethod.POST, new HttpEntity<>(game1, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //addAdminGame
        Game game2 = new Game("1234", "game2", true);
        ResponseEntity<Void> responseEntity2 = testRestTemplate.exchange("/api/game/admin", HttpMethod.POST, new HttpEntity<>(game2, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //getAllGames
        ResponseEntity<Game[]> forEntity1 = testRestTemplate.exchange("/api/game", HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game[].class);
        Assertions.assertThat(forEntity1.getBody()).hasSize(2);

        //getOneById
        ResponseEntity<Game> forEntity2 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity2.getBody()).getGameName()).isEqualTo("game1");

        //editGame
        testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Game.class);
        ResponseEntity<Game> forEntity3 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity3.getBody()).isApproved()).isEqualTo(true);

    }

    private HttpHeaders createHeader(String token){
        String headerValue = "Bearer " + token;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", headerValue);
        return httpHeaders;
    }

}
