package com.journalApp.journalAppTwo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.journalApp.journalAppTwo.Repository.UserRepository;
import com.journalApp.journalAppTwo.entity.User;

public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        userRepository.save(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
