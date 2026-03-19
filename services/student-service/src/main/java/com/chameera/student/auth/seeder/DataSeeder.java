package com.chameera.student.auth.seeder;


import com.chameera.student.auth.model.Role;
import com.chameera.student.auth.model.User;
import com.chameera.student.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin@1234"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user seeded.");
        }
    }
}