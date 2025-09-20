package com.journalApp.journalAppTwo.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journalApp.journalAppTwo.entity.User;
import com.journalApp.journalAppTwo.services.UserServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public User creatUser(@RequestBody User user) {
        return userServices.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userServices.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userServices.saveEntry(userInDb);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    // DeleteMapping("/deleteAll")
    // public void deleteAll() {
    //     userServices.deleteAll();
    // }

    // DeleteMapping("/DeleteById")
    // public void deleteByID(ObjectId myId) {
    //     userServices.deleteById(myId);
    // }

}
