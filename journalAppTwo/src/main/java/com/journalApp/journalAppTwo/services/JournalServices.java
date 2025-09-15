package com.journalApp.journalAppTwo.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalApp.journalAppTwo.Repository.JournalEntryRepository;
import com.journalApp.journalAppTwo.entity.JournalEntity;

@Service
public class JournalServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntity saveEntry(JournalEntity journalEntity) {
        return journalEntryRepository.save(journalEntity);

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
