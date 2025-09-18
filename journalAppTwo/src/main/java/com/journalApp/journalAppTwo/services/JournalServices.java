package com.journalApp.journalAppTwo.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalApp.journalAppTwo.Repository.JournalEntryRepository;
import com.journalApp.journalAppTwo.Repository.UserRepository;
import com.journalApp.journalAppTwo.entity.JournalEntity;
import com.journalApp.journalAppTwo.entity.User;

@Service
public class JournalServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;

    public JournalEntity saveEntry(JournalEntity myEntry, String userName) { // postmapping
        User user = userServices.findByUserName(userName);
        JournalEntity saved = journalEntryRepository.save(myEntry);
        user.getGetJournalEntries().add(saved);
        userServices.saveEntry(user);

        // JournalEntity entryInDb = journalEntryRepository.save(journalEntity);
        // System.out.println(entryInDb.toString());
        // User myUser = userRepository.findByUserName(userName);
        // System.out.println(myUser);
        // myUser.getGetJournalEntries().add(entryInDb.getId());
        // userRepository.save(myUser);
        return journalEntryRepository.save(myEntry);

    }

    public List<JournalEntity> getAllEntries() {
        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntity> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);

    }

    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

    public void deleteByID(ObjectId myId) {
        journalEntryRepository.deleteById(myId);
    }

}
