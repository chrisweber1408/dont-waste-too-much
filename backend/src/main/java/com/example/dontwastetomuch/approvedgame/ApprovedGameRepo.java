package com.example.dontwastetomuch.approvedgame;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ApprovedGameRepo extends MongoRepository<ApprovedGame, String> {

}
