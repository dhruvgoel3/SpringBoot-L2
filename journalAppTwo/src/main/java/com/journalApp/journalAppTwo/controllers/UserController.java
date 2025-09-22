package com.journalApp.journalAppTwo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.journalApp.journalAppTwo.entity.User;
import com.journalApp.journalAppTwo.services.UserServices;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/getAllUsers")
    public List<User> getAll() {
        return userServices.getAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userServices.saveEntry(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userServices.findByUserName(userName);
        if (userInDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
            userInDb.setUserName(user.getUserName().trim());
        }
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            userInDb.setPassword(user.getPassword());
        }
        userServices.saveEntry(userInDb);
        return ResponseEntity.noContent().build();
    }

}
