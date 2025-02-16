// src/main/java/lt/ca/javau11/config/DatabaseInitializer.java
package lt.ca.javau11.config;

import lt.ca.javau11.entities.Role;
import lt.ca.javau11.entities.User;
import lt.ca.javau11.repositories.RoleRepository;
import lt.ca.javau11.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create roles if they don't exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                roleRepository.save(userRole);
            }

            // Create an admin user if one doesn't exist
            if (!userRepository.existsByUsername("admin")) {
                User adminUser = new User();
                adminUser.setName("Administrator");
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("password")); // Use a strong password!
                adminUser.setRoles(Collections.singleton(adminRole));
                userRepository.save(adminUser);
            }

            if (!userRepository.existsByUsername("user")) {
                 User regularUser = new User();
                 regularUser.setName("User");
                 regularUser.setUsername("user");
                 regularUser.setEmail("user@example.com");
                 regularUser.setPassword(passwordEncoder.encode("password"));
                 regularUser.setRoles(Collections.singleton(userRole));
                 userRepository.save(regularUser);
            }
        };
    }
}