package com.chameera.student.student.dto;

import jakarta.validation.constraints.*;

// https://spring.io/guides/gs/validating-form-input
public record CreateStudentRequest(
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be at most 120")
    Integer age,

    Long userId

) {}