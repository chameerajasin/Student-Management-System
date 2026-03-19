package com.chameera.student.auth.dto;

public record LoginRequest(
        String username,
        String password
) {
}