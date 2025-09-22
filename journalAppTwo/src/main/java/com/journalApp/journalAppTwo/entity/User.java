package com.journalApp.journalAppTwo.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usersTwo")
@Data
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userName;

    private String password;

    // renamed field to be clearer
    @DBRef
    private List<JournalEntity> journalEntries = new ArrayList<>();
}
    