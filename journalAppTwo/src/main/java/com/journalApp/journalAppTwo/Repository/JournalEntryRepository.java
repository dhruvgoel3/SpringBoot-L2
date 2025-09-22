package com.journalApp.journalAppTwo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalApp.journalAppTwo.entity.JournalEntity;

public interface JournalEntryRepository extends MongoRepository<JournalEntity, ObjectId> {

}