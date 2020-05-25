/*
package com.intern.book.configurations;

import com.intern.blog.models.dao.Role;
import com.intern.blog.models.dao.User;
import com.intern.blog.repositories.RoleRepository;
import com.intern.blog.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${jwt-key}")
    private String signingKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private void addRoleIfMissing(String name, String description) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new Role(name, description));
        }
    }

    private void addUserIfMissing(String username, String password, String... roles) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User(username, "John", "Doe", new BCryptPasswordEncoder().encode(password));
            user.setRoles(new HashSet<>());

            for (String role : roles) {
                user.getRoles().add(roleRepository.findByName(role));
            }
            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addRoleIfMissing("ROLE_ADMIN", "Administrators");
        addRoleIfMissing("ROLE_STAFF", "Staffs");

        addUserIfMissing("user", "fun123", "ROLE_STAFF");
        addUserIfMissing("admin", "fun123", "ROLE_STAFF", "ROLE_ADMIN");

        if (signingKey == null || signingKey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("blog")
                    .signWith(SignatureAlgorithm.HS256, "blog").compact();
        }
    }
}
*/
