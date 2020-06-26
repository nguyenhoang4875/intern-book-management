package com.intern.book.controllers;

import com.intern.book.models.dao.User;
import com.intern.book.models.dto.UploadFileResponse;
import com.intern.book.models.dto.UserDetailDto;
import com.intern.book.services.FileStorageService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload-avatar")
    public UploadFileResponse uploadFile(Principal principal, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        User user = userService.findOneByUsername(principal.getName());
        user.setAvatar(fileName);
        userService.save(user);
        return new UploadFileResponse(fileName, file.getContentType(), file.getSize(), "SUCCESS");
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<UserDetailDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<UserDetailDto> getUsersByRole() {
        return userService.getUsersByRole("ROLE_USER");
    }
    @GetMapping("/{userId}")
    public UserDetailDto getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public UserDetailDto updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDetailDto userDetailDto) {
        userDetailDto.setId(userId);
        return userService.update(userDetailDto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.delete(userId);
    }

}
