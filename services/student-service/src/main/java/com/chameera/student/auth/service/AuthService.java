package com.chameera.student.auth.service;

import com.chameera.student.auth.dto.RegisterRequest;
import com.chameera.student.auth.model.Role;
import com.chameera.student.auth.model.User;
import com.chameera.student.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User register(RegisterRequest request){

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        return userRepository.save(user);
    }
}
