package com.journalApp.journalAppTwo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalApp.journalAppTwo.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

}
