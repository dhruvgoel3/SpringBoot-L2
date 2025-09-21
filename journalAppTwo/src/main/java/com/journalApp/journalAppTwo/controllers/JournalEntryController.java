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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal") // lowercase path is conventional
public class JournalEntryController {

    @Autowired
    private JournalServices journalServices;

    @Autowired
    private UserServices userServices;

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry,
            @PathVariable String userName) {
        try {
            journalServices.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public List<JournalEntity> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userServices.findByUserName(userName);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User '" + userName + "' not found");
        }
        return user.getGetJournalEntries();
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntity> journalEntry = journalServices.findById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalServices.deleteByID(myId, userName);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        journalServices.deleteAll();
    }

    @PutMapping("{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntity newEntry,
            @PathVariable String userName) {

        // find existing entry or throw if not found
        JournalEntity old = journalServices.findById(myId)
                .orElseThrow(() -> new RuntimeException("Journal entry not found: " + myId));
        System.out.println(myId);

        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                    : old.getTitle());

            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
                    : old.getContent());
            journalServices.saveEntry(old, userName);
            System.out.println(newEntry);
            return new ResponseEntity<>(old, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
// In this file only put mapping is not working properly .
// There are some some errors in updation of code