package com.example.dontwastetomuch.unapprovedgame;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnapprovedGameRepo extends MongoRepository<UnapprovedGame, String> {
}
