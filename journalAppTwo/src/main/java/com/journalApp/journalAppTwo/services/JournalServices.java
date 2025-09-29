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

    // Save or update an entry and ensure user's list is updated
    public JournalEntity saveEntry(JournalEntity myEntry, String userName) {
        // set date if null
        myEntry.ensureDate();

        JournalEntity saved = journalEntryRepository.save(myEntry);

        // add to user journal list if user exists
        User user = userServices.findByUserName(userName);
        if (user != null) {
            boolean already = user.getJournalEntries().stream()
                    .anyMatch(e -> e.getId() != null && e.getId().equals(saved.getId()));
            if (!already) {
                user.getJournalEntries().add(saved);
                userServices.saveEntry(user);
            } else {
                // If already present, optionally update the DBRef list by replacing the entry object
                // (keep it simple: just save the user so DBRef stays consistent)
                userServices.saveEntry(user);
            }
        }
        return saved;
    }

    // simple save (no user linking) - kept for any direct save needs
    public JournalEntity saveEntryTwo(JournalEntity myEntry) {
        myEntry.ensureDate();
        return journalEntryRepository.save(myEntry);
    }

    public List<JournalEntity> getAllEntries(String userName) {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

    public boolean existsById(ObjectId id) {
        return journalEntryRepository.existsById(id);
    }

    public void deleteByID(ObjectId myId, String userName) {
        User user = userServices.findByUserName(userName);
        if (user != null) {
            user.getJournalEntries().removeIf(x -> x.getId() != null && x.getId().equals(myId));
            userServices.saveEntry(user);
        }
        if (journalEntryRepository.existsById(myId)) {
            journalEntryRepository.deleteById(myId);
        }
    }

}
