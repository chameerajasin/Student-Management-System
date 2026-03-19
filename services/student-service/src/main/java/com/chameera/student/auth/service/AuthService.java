package com.chameera.student.auth.service;

import com.chameera.student.auth.model.User;
import com.chameera.student.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User login(User user) {
//        What authenticationManager.authenticate(...) does
//
//        It verifies the credentials against your UserDetailsService. Internally:
//
//        1. Calls UserService.loadUserByUsername(username) to fetch the user from DB
//        2. Compares the raw password against the stored BCrypt hash using PasswordEncoder
//        3. If credentials are wrong → throws BadCredentialsException (Spring handles this as 401)
//        4. If user not found → throws UsernameNotFoundException
//
//        So it acts as the credential validation gate — if it passes, you know the user exists and the password is correct.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        return userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
