package com.intern.book.services.servicesIplm;

import com.intern.book.configurations.TokenProvider;
import com.intern.book.converter.bases.Converter;
import com.intern.book.exeptions.ConflictException;
import com.intern.book.exeptions.NotFoundException;
import com.intern.book.models.dao.Role;
import com.intern.book.models.dao.User;
import com.intern.book.models.dto.Login;
import com.intern.book.models.dto.UserDetailDto;
import com.intern.book.models.dto.UserDto;
import com.intern.book.repositories.UserRepository;
import com.intern.book.services.RoleService;
import com.intern.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Converter<User, UserDto> userDaoToUserDtoConverter;

    @Autowired
    private Converter<User, UserDetailDto> userDaoToUserDetailDtoConverter;

    @Override
    public List<UserDetailDto> getAllUsers() {
        return userDaoToUserDetailDtoConverter.convert(userRepository.findAll());
    }


    @Override
    public List<UserDetailDto> getUsersByRole(String role) {
        return userDaoToUserDetailDtoConverter.convert(userRepository.findByRolesName(role));
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<?> login(Login login) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        UserDto userDto = userDaoToUserDtoConverter.convert(userService.findOneByUsername(login.getUsername()));
        userDto.setExpired(tokenProvider.getExpirationDateFromToken(token));
        userDto.setToken(token);
        return ResponseEntity.ok(userDto);
    }

    public User registerAccount(@Valid @RequestBody User user) {
        User existingUser = userService.findOneByUsername((user.getUsername()));
        if (existingUser != null) {
            throw new ConflictException("This username was exist");
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Set<Role> initRoles = new HashSet<>();
            initRoles.add(roleService.findOneByName("ROLE_USER"));
            user.setRoles(initRoles);
            return userService.save(user);
        }
    }

    @Override
    public UserDetailDto update(UserDetailDto userDetailDto) {
        User user = userRepository.getOne(userDetailDto.getId());
        user.setUsername(userDetailDto.getUsername());
        user.setFirstName(userDetailDto.getFirstName());
        user.setLastName(userDetailDto.getLastName());
        user.setEmail(userDetailDto.getEmail());
        user.setEnabled(userDetailDto.isEnabled());
        user.setAvatar(userDetailDto.getAvatar());
        userRepository.save(user);
        return userDetailDto;
    }

    @Override
    public void delete(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }


    @Override
    public UserDetailDto getUserById(Integer userId) {
        return userDaoToUserDetailDtoConverter.convert(userRepository.getOne(userId));
    }

    @Override
    public boolean checkRoleAdmin() {
        User user = getCurrentUser();

        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

}

