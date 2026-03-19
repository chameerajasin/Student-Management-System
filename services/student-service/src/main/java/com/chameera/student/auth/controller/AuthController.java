package com.chameera.student.auth.controller;

import com.chameera.student.auth.dto.AuthResponse;
import com.chameera.student.auth.dto.LoginRequest;
import com.chameera.student.auth.dto.RegisterRequest;
import com.chameera.student.auth.model.User;
import com.chameera.student.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role());

        User createdUser = authService.register(user);

        AuthResponse authResponse = new AuthResponse(createdUser.getId(), createdUser.getUsername(), createdUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public  ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());

        User dbUser = authService.login(user);

        AuthResponse authResponse = new AuthResponse(dbUser.getId(), dbUser.getUsername(), dbUser.getRole());

        return  ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

}