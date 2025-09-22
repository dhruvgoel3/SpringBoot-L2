package com.journalApp.journalAppTwo.controllers;

import com.journalApp.journalAppTwo.entity.JournalEntity;
import com.journalApp.journalAppTwo.entity.User;
import com.journalApp.journalAppTwo.services.JournalServices;
import com.journalApp.journalAppTwo.services.UserServices;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalServices journalServices;

    @Autowired 
    private UserServices userServices;

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry,
            @PathVariable String userName) {
        User user = userServices.findByUserName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        myEntry.ensureDate();
        JournalEntity saved = journalServices.saveEntry(myEntry, userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntity>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userServices.findByUserName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user.getJournalEntries());
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId) {
        ObjectId id;
        try {
            id = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
        Optional<JournalEntity> journalEntry = journalServices.findById(id);
        return journalEntry.map(entry -> ResponseEntity.ok(entry))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE: correct path includes userName and myId
    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName, @PathVariable String myId) {
        ObjectId id;
        try {
            id = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
        User user = userServices.findByUserName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!journalServices.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }
        journalServices.deleteByID(id, userName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        journalServices.deleteAll();
        return ResponseEntity.noContent().build();
    }

    // PUT: update an entry for a user
    @PutMapping("/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable String userName,
            @PathVariable String myId,
            @RequestBody JournalEntity newEntry) {

        ObjectId id;
        try {
            id = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }

        // find existing entry or return not found
        JournalEntity old = journalServices.findById(id)
                .orElse(null);

        if (old == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }

        // update only non-null / non-empty fields
        if (newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()) {
            old.setTitle(newEntry.getTitle().trim());
        }
        if (newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty()) {
            old.setContent(newEntry.getContent().trim());
        }
        // keep date as-is unless user sets it explicitly
        if (newEntry.getDate() != null) {
            old.setDate(newEntry.getDate());
        }

        JournalEntity saved = journalServices.saveEntry(old, userName);
        return ResponseEntity.ok(saved);
    }

}
