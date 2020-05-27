package com.intern.book.controllers;


import com.intern.book.configurations.TokenProvider;
import com.intern.book.exeptions.NotFoundException;
import com.intern.book.models.dao.Role;
import com.intern.book.models.dao.User;
import com.intern.book.models.dto.AuthToken;
import com.intern.book.models.dto.Login;
import com.intern.book.services.RoleService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public User registerAccount(@Valid @RequestBody User user) {
        User existingUser = userService.findOneByUsername((user.getUsername()));
        if (existingUser != null) {
            throw new NotFoundException("This username was exist");
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Set<Role> initRoles = new HashSet<>();
            initRoles.add(roleService.findOneByName("ROLE_USER"));
            user.setRoles(initRoles);
            return userService.save(user);
        }
    }
}
