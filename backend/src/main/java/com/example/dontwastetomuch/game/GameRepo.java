package com.example.dontwastetomuch.game;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends MongoRepository<Game, String> {

}
