package com.chameera.student.student.exception;


import lombok.Getter;

@Getter
public class StudentNotFoundException extends RuntimeException {
    private final int statusCode;

    //these were created to get the idea. we can create custom constructors if needed for different use cases
//    public StudentNotFoundException(String message) {
//        super(message);
//        statusCode = 404;
//    }
//
//    public StudentNotFoundException(String message, Throwable cause) {
//        super(message, cause);
//        statusCode = 404;
//    }

    public StudentNotFoundException(Long studentId) {
        super("Student with ID " + studentId + " not found");
        statusCode = 404;
    }

}