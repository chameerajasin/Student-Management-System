package com.chameera.student.student.exception;


import lombok.Getter;

@Getter
public class StudentNotFoundException extends RuntimeException {
    private final int statusCode;

    public StudentNotFoundException(String username) {
        super("Student not found for username: " + username);
        statusCode = 404;
    }

    public StudentNotFoundException(Long studentId) {
        super("Student with ID " + studentId + " not found");
        statusCode = 404;
    }

}