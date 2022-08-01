package com.example.dontwastetomuch.game;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GameRepo extends MongoRepository<Game, String> {

    Optional<Game> findByGameName(String gameName);

}
