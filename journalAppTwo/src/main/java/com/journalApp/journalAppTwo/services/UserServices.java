package com.journalApp.journalAppTwo.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalApp.journalAppTwo.Repository.UserRepository;
import com.journalApp.journalAppTwo.entity.User;
@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User saveEntry(User user) { // save user
        return userRepository.save(user);

    }

    public List<User> getAll() { // getAll users
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myId) {
        return userRepository.findById(myId);

    }

    public void deleteById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
