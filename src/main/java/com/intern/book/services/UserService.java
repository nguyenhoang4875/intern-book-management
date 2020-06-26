package com.intern.book.services;

import com.intern.book.models.dao.User;
import com.intern.book.models.dto.Login;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(int id);

    User save(User user);

    void deleteById(int id);

    User getCurrentUser();

    User findOneByUsername(String username);

    ResponseEntity<?> login(Login login);

    User registerAccount(User user);

    boolean checkRoleAdmin();
}
