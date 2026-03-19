package com.chameera.student.student;

import com.chameera.student.student.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller: request and response should be a dto: Data Transfer Object
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // get students : GET http method
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<GetStudentResponse>> getStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // service call to get students
        // List<Student> students = studentService.getStudents();
        Page<Student> studentsPage = studentService.getStudents(pageable);

        // mapping model to dto
        // List<GetStudentResponse> studentDtos = students.stream()
        //         .map(s -> new GetStudentResponse(s.getId(), s.getName()))
        //         .toList();
        Page<GetStudentResponse> studentDtos = studentsPage
                .map(s -> new GetStudentResponse(s.getId(), s.getName()));

        // return response dto as a http response
        return ResponseEntity.ok(studentDtos);
    }

    // get student by id : GET http method
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<GetStudentResponse> getStudentById(@PathVariable Long id) {
        //service call to get student
        Student student = studentService.getStudentById(id);

        //mapping model to dto
        GetStudentResponse studentDto = new GetStudentResponse(student.getId(), student.getName());


       return ResponseEntity.ok(studentDto);

    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<GetStudentResponse> getMyProfile(Authentication authentication) {
        Student student = studentService.getStudentByUsername(authentication.getName());
        return ResponseEntity.ok(new GetStudentResponse(student.getId(), student.getName()));
    }

    //TODO: convert this method to return Page info
    // get student by age : GET http method
    @GetMapping("/age/{age}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<GetStudentResponse>> getStudentsByAge(@PathVariable int age){
        //service call to get students by age
        List<Student> studentsByAge = studentService.getStudentsByAge(age);

        //mapping model to dto
        List<GetStudentResponse> studentAgeDtos = studentsByAge.stream()
                .map((s)-> new GetStudentResponse(s.getId(), s.getName()) ).toList();

        return ResponseEntity.ok(studentAgeDtos);
    }

    // create student: POST http method
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest studentRequest) {
        // map CreateStudentRequest to Student
        Student student = new Student(studentRequest.name(), studentRequest.age());

        // call student/id service createStudent() method with Student
        Student createdStudent = studentService.createStudent(student, studentRequest.userId());

        // map student to CreateStudentResponse
        CreateStudentResponse studentDto = new CreateStudentResponse(createdStudent.getId());

        // return CreateStudentResponse as a http response
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto);
    }

    // update student
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UpdateStudentResponse> getStudentByAge(@PathVariable Long id,@Valid @RequestBody UpdateStudentRequest studentRequest){
        //mapping updateRequest to student
        Student student = new Student(studentRequest.name(), studentRequest.age());
        student.setId(id);

        //service call  to update student
        Student updateStudent = studentService.updateStudent(student);

        // map to dto
        UpdateStudentResponse updateStudentResponse = new UpdateStudentResponse(updateStudent.getId(), updateStudent.getName(), updateStudent.getAge());

        return ResponseEntity.ok(updateStudentResponse);
    }

    @PatchMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<UpdateStudentResponse> updateMyProfile(Authentication authentication, @Valid @RequestBody UpdateStudentRequest studentRequest) {
        String username = authentication.getName();
        Student updates = new Student(studentRequest.name(), studentRequest.age());
        Student updatedStudent = studentService.updateStudentByUsername(username, updates, studentRequest.password());
        return ResponseEntity.ok(new UpdateStudentResponse(updatedStudent.getId(), updatedStudent.getName(), updatedStudent.getAge()));
    }

    // delete student : DELETE http method
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        // call student service deleteStudent()
        studentService.deleteStudent(id);

        // return http request
        return ResponseEntity.noContent().build();
    }

}
