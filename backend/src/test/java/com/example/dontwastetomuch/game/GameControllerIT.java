package com.example.dontwastetomuch.game;


import com.example.dontwastetomuch.dto.UserGameDTO;
import com.example.dontwastetomuch.user.LoginData;
import com.example.dontwastetomuch.user.LoginResponse;
import com.example.dontwastetomuch.user.MyUserCreationData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
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
        Game game1 = new Game("123", "FIFA 22", false);
        ResponseEntity<Void> responseEntity1 = testRestTemplate.exchange("/api/game/user", HttpMethod.POST, new HttpEntity<>(game1, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //addAdminGame
        Game game2 = new Game("1234", "game2", true);
        Game game3 = new Game("1235", "game3", true);
        Game game4 = new Game("1236", "game4", true);
        ResponseEntity<Void> responseEntity2 = testRestTemplate.exchange("/api/game/admin", HttpMethod.POST, new HttpEntity<>(game2, createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/admin", HttpMethod.POST, new HttpEntity<>(game3, createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/admin", HttpMethod.POST, new HttpEntity<>(game4, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //getAllGames
        ResponseEntity<Game[]> forEntity1 = testRestTemplate.exchange("/api/game", HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game[].class);
        Assertions.assertThat(forEntity1.getBody()).hasSize(4);

        //addGamesToMyList
        ResponseEntity<Void> responseEntity3 = testRestTemplate.exchange("/api/game/myGames/" + game1.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/myGames/" + game2.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/myGames/" + game3.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.OK);

        //getOneById
        ResponseEntity<Game> forEntity2 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity2.getBody()).getGameName()).isEqualTo("FIFA 22");

        //switchStatus
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<Game> forEntity3 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity3.getBody()).isApproved()).isEqualTo(true);

        //listAllMyGames
        ResponseEntity<UserGameDTO[]> exchange1 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        Assertions.assertThat(exchange1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange1.getBody()).hasSize(3);

        //updateGameStatsFromOneOfMyGames
        UserGameDTO userGameDTO = new UserGameDTO("Hans", "FIFA 22", game1.getId(), 0, 0, true);
        double oldPlaytime = userGameDTO.getPlaytime();
        double oldSpentMoney = userGameDTO.getSpentMoney();
        userGameDTO.setSpentMoney(10);
        userGameDTO.setPlaytime(120);
        ResponseEntity<Void> exchange4 = testRestTemplate.exchange("/api/game/myGames/update", HttpMethod.PUT, new HttpEntity<>(userGameDTO, createHeader(token)), Void.class);
        Assertions.assertThat(exchange4.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<UserGameDTO[]> exchange5 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        double playtime = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getPlaytime();
        double spentMoney = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoney();
        Assertions.assertThat(oldPlaytime).isNotEqualTo(playtime);
        Assertions.assertThat(oldSpentMoney).isNotEqualTo(spentMoney);
        Assertions.assertThat(playtime).isEqualTo(120);
        Assertions.assertThat(spentMoney).isEqualTo(10);

        //removeOneGameFromMyList
        ResponseEntity<Void> exchange2 = testRestTemplate.exchange("/api/game/myGames/" + game1.getId(), HttpMethod.DELETE, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange2.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<UserGameDTO[]> exchange3 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        Assertions.assertThat(exchange3.getBody()).hasSize(2);
    }

    private HttpHeaders createHeader(String token){
        String headerValue = "Bearer " + token;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", headerValue);
        return httpHeaders;
    }

}
