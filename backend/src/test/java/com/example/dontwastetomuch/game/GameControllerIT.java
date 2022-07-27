package com.example.dontwastetomuch.game;


import com.example.dontwastetomuch.dto.UserGameDTO;
import com.example.dontwastetomuch.user.*;
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
    @Autowired
    private MyUserRepo myUserRepo;

    private void setUserAsAdmin(String username){
        MyUser user = myUserRepo.findByUsername(username).orElseThrow();
        user.setRoles(List.of("admin"));
        myUserRepo.save(user);
    }

    @Test
    void shouldTestTheController(){

        //addUser
        MyUserCreationData user1 = new MyUserCreationData();
        user1.setUsername("hans");
        user1.setPassword("123");
        user1.setPasswordRepeat("123");
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/api/users", user1, Void.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //loginUser
        LoginData loginData = new LoginData();
        loginData.setUsername("hans");
        loginData.setPassword("123");
        ResponseEntity<LoginResponse> loginResponseResponseEntity = testRestTemplate.postForEntity("/api/login", loginData, LoginResponse.class);
        Assertions.assertThat(loginResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(loginResponseResponseEntity.getBody()).getJwt()).isNotBlank();

        //addUserGames
        String token = loginResponseResponseEntity.getBody().getJwt();
        Game game1 = new Game("1", "FIFA 22");
        Game game2 = new Game("2", "game2");
        Game game3 = new Game("3", "game3");
        Game game4 = new Game("4", "game4");
        ResponseEntity<Void> responseEntity1 = testRestTemplate.exchange("/api/game", HttpMethod.POST, new HttpEntity<>(game1, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        testRestTemplate.exchange("/api/game", HttpMethod.POST, new HttpEntity<>(game2, createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game", HttpMethod.POST, new HttpEntity<>(game3, createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game", HttpMethod.POST, new HttpEntity<>(game4, createHeader(token)), Void.class);

        //getAllGames
        ResponseEntity<Game[]> forEntity1 = testRestTemplate.exchange("/api/game", HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game[].class);
        Assertions.assertThat(forEntity1.getBody()).hasSize(4);

        //addGamesToMyList
        ResponseEntity<Void> responseEntity3 = testRestTemplate.exchange("/api/game/myGames/" + game1.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/myGames/" + game2.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        testRestTemplate.exchange("/api/game/myGames/" + game3.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.OK);

        //getOneOfMyGamesByIdCreatedByUser
        ResponseEntity<Game> forEntity2 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity2.getBody()).getGameName()).isEqualTo("FIFA 22");
        Assertions.assertThat(Objects.requireNonNull(forEntity2.getBody()).isApproved()).isEqualTo(false);

        //switchStatusShouldFallAsUser
        Assertions.assertThat(forEntity2.getBody().isApproved()).isEqualTo(false);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        ResponseEntity<Game> forEntity3 = testRestTemplate.exchange("/api/game/" + game1.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(forEntity3.getBody()).isApproved()).isEqualTo(false);

        //listAllMyGames
        ResponseEntity<UserGameDTO[]> exchange1 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        Assertions.assertThat(exchange1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(exchange1.getBody()).hasSize(3);

        //updateGameStatsFromOneOfMyGames
        UserGameDTO userGameDTO = new UserGameDTO("Hans", "FIFA 22", game1.getId(), 0, 0,  0, 0, true);
        double oldPlaytime = userGameDTO.getPlaytime();
        double oldSpentMoneyGame = userGameDTO.getSpentMoneyGame();
        double oldSpentMoneyCoins = userGameDTO.getSpentMoneyCoins();
        double oldSpentMoneyGamePass = userGameDTO.getSpentMoneyGamePass();
        userGameDTO.setSpentMoneyGame(60);
        userGameDTO.setSpentMoneyCoins(25);
        userGameDTO.setSpentMoneyGamePass(10);
        userGameDTO.setPlaytime(120);
        ResponseEntity<Void> exchange4 = testRestTemplate.exchange("/api/game/myGames/update", HttpMethod.PUT, new HttpEntity<>(userGameDTO, createHeader(token)), Void.class);
        Assertions.assertThat(exchange4.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<UserGameDTO[]> exchange5 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        double playtime = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getPlaytime();
        double spentMoneyCoins = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyCoins();
        double spentMoneyGame = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyGame();
        double spentMoneyGamePass = Arrays.stream(Objects.requireNonNull(exchange5.getBody())).filter(gameData -> game1.getId().equals(gameData.getGameId())).findAny().orElseThrow().getSpentMoneyGamePass();
        Assertions.assertThat(oldPlaytime).isNotEqualTo(playtime);
        Assertions.assertThat(oldSpentMoneyGame).isNotEqualTo(spentMoneyGame);
        Assertions.assertThat(oldSpentMoneyCoins).isNotEqualTo(spentMoneyCoins);
        Assertions.assertThat(oldSpentMoneyGamePass).isNotEqualTo(spentMoneyGamePass);
        Assertions.assertThat(playtime).isEqualTo(120);
        Assertions.assertThat(spentMoneyCoins).isEqualTo(25);
        Assertions.assertThat(spentMoneyGame).isEqualTo(60);
        Assertions.assertThat(spentMoneyGamePass).isEqualTo(10);

        //removeOneGameFromMyList
        ResponseEntity<Void> exchange2 = testRestTemplate.exchange("/api/game/myGames/" + game1.getId(), HttpMethod.DELETE, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange2.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<UserGameDTO[]> exchange3 = testRestTemplate.exchange("/api/game/myGames", HttpMethod.GET, new HttpEntity<>(createHeader(token)), UserGameDTO[].class);
        Assertions.assertThat(exchange3.getBody()).hasSize(2);

        //addAdminRole
        setUserAsAdmin(user1.getUsername());

        //addAdminGame
        Game adminGame = new Game("5","Fall Guys");
        ResponseEntity<Void> responseEntityAdmin = testRestTemplate.exchange("/api/game", HttpMethod.POST, new HttpEntity<>(adminGame, createHeader(token)), Void.class);
        Assertions.assertThat(responseEntityAdmin.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //addOneGameToMyList
        ResponseEntity<Void> exchange6 = testRestTemplate.exchange("/api/game/myGames/" + adminGame.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange6.getStatusCode()).isEqualTo(HttpStatus.OK);

        //getOneOfMyGamesByIdCreatedByAdmin
        ResponseEntity<Game> exchange7 = testRestTemplate.exchange("/api/game/" + adminGame.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(exchange7.getBody()).getGameName()).isEqualTo("Fall Guys");
        Assertions.assertThat(Objects.requireNonNull(exchange7.getBody()).isApproved()).isEqualTo(true);

        //switchStatusAsAdmin
        Assertions.assertThat(exchange7.getBody().isApproved()).isEqualTo(true);
        ResponseEntity<Void> exchange8 = testRestTemplate.exchange("/api/game/" + adminGame.getId(), HttpMethod.PUT, new HttpEntity<>(createHeader(token)), Void.class);
        Assertions.assertThat(exchange8.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<Game> exchange9 = testRestTemplate.exchange("/api/game/" + adminGame.getId(), HttpMethod.GET, new HttpEntity<>(createHeader(token)), Game.class);
        Assertions.assertThat(Objects.requireNonNull(exchange9.getBody()).isApproved()).isEqualTo(false);

    }

    private HttpHeaders createHeader(String token){
        String headerValue = "Bearer " + token;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", headerValue);
        return httpHeaders;
    }

}
