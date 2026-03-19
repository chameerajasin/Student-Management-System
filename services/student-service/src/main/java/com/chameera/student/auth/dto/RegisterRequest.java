package com.chameera.student.auth.dto;

import com.chameera.student.auth.model.Role;

public record RegisterRequest(
        String username,
        String password,
        Role role
) {

}
