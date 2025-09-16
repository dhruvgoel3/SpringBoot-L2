package com.journalApp.journalAppTwo.controllers;

import com.journalApp.journalAppTwo.entity.JournalEntity;
import com.journalApp.journalAppTwo.services.JournalServices;
import com.journalApp.journalAppTwo.services.UserServices;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal") // lowercase path is conventional
public class JournalEntryController {

    @Autowired
    private JournalServices journalServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("/{userName}")
    public List<JournalEntity> getAllJournalEntriesOfUser(@PathVariable String userName) {
        return journalServices.getAllEntries();
    }

    @GetMapping("/id/{myId}")
    public Optional<JournalEntity> getJournalEntryById(@PathVariable ObjectId myId) {
        return journalServices.findById(myId);
    }

    @PostMapping
    public JournalEntity createEntry(@RequestBody JournalEntity myEntry) {
        myEntry.setDate(LocalDateTime.now());
        return journalServices.saveEntry(myEntry);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteJournalEntryById(@PathVariable ObjectId myId) {
        // Optional<JournalEntity> entry = journalServices.findById(myId);
        journalServices.deleteByID(myId);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        journalServices.deleteAll();
    }

    @PutMapping("/id/{id}")
    public JournalEntity updateJournalEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntity newEntry) {

        // find existing entry or throw if not found
        JournalEntity old = journalServices.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal entry not found: " + id));

        // update only if new values are provided and not empty
        old.setTitle(
                newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());

        old.setContent(
                newEntry.getContent() != null && !newEntry.getContent().isEmpty()
                        ? newEntry.getContent()
                        : old.getContent());

        // persist the changes
        return journalServices.saveEntry(old);
    }

}
