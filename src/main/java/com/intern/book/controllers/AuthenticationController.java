package com.intern.book.controllers;


import com.intern.book.models.dao.User;
import com.intern.book.models.dto.Login;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return userService.login(login);
    }

    @PostMapping("/register")
    public User registerAccount(@Valid @RequestBody User user) {
        return userService.registerAccount(user);
    }

    @GetMapping("/admin")
    public boolean checkRoleAdmin(){
        return  userService.checkRoleAdmin();
    }
}
