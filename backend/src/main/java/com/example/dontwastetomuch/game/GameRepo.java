package com.example.dontwastetomuch.game;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameRepo extends MongoRepository<Game, String> {
}
