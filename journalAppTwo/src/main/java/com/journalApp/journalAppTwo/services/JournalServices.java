package com.journalApp.journalAppTwo.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalApp.journalAppTwo.Repository.JournalEntryRepository;
import com.journalApp.journalAppTwo.entity.JournalEntity;
import com.journalApp.journalAppTwo.entity.User;

@Service
public class JournalServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;

    // Creating a entry in DB
    public JournalEntity saveEntry(JournalEntity myEntry, String userName) { // postmapping
        User user = userServices.findByUserName(userName);
        JournalEntity saved = journalEntryRepository.save(myEntry);
        user.getGetJournalEntries().add(saved);
        userServices.saveEntry(user);
        return journalEntryRepository.save(myEntry);

    }
     public JournalEntity saveEntryTwo(JournalEntity myEntry) { // postmapping 2
        return journalEntryRepository.save(myEntry);
        

    }

    // Get all entries
    public List<JournalEntity> getAllEntries(String userName) {
        return journalEntryRepository.findAll();

    }

    // Get entries using ID
    public Optional<JournalEntity> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);

    }

    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

    public void deleteByID(ObjectId myId, String userName) {
        User user = userServices.findByUserName(userName);
        user.getGetJournalEntries().removeIf(x -> x.getId().equals(myId));
        userServices.saveEntry(user);
        journalEntryRepository.deleteById(myId);

    }

}
