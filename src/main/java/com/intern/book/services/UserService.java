package com.intern.book.services;

import com.intern.book.models.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> findAll();

    public Optional<User> findById(int id);

    public User save(User user);

    public void deleteById(int id);

    public User getCurrentUser();

    User findOneByUsername(String username);
}
