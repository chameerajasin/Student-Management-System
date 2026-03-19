package com.chameera.student.auth.dto;

import com.chameera.student.auth.model.Role;

public record AuthResponse(
        long id,
        String username,
        Role role
) {
}
