package com.journalApp.journalAppTwo.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntity {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

    // helper to set date if not present
    public void ensureDate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }
}
