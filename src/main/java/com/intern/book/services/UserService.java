package com.intern.book.services;


import com.intern.book.models.dao.User;
import com.intern.book.models.dto.Login;
import com.intern.book.models.dto.UserDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    ResponseEntity<?> login(Login login);

    User registerAccount(@Valid @RequestBody User user);

    List<UserDetailDto> getAllUsers();

    List<UserDetailDto> getUsersByRole(String role);

    User save(User user);

    User findOneByUsername(String username);


    UserDetailDto update(UserDetailDto userDetailDto);

    void delete(Integer userId);

    User getCurrentUser();

    UserDetailDto getUserById(Integer userId);

    boolean checkRoleAdmin();
}
