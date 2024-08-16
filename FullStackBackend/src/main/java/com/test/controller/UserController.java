package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.User;
import com.test.exception.UserNotFoundException;
import com.test.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userrepo;
    
    @PostMapping("/add")
    public User adduser(@RequestBody User user) {
        return userrepo.save(user);
    }
    
    @GetMapping("/display")
    public List<User> displayuser() {
        return userrepo.findAll();
    }
    
    @GetMapping("/display/{id}")
    public User displayuser(@PathVariable long id) {
        return userrepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @PutMapping("/update/{id}")
    public User updateuser(@RequestBody User newUser, @PathVariable long id) {
        return userrepo.findById(id)
            .map(user -> {
                user.setUsername(newUser.getUsername());
                user.setName(newUser.getName());
                user.setEmail(newUser.getEmail());
                return userrepo.save(user);
            })
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteuser(@PathVariable Long id) {
        return userrepo.findById(id)
            .map(user -> {
                userrepo.deleteById(id);
                return "Deleted";
            })
            .orElse("User not found");
    }
}
