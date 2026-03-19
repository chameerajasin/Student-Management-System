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
    ResponseEntity<GetStudentResponse> getStudentById(@PathVariable Long id) {
        //service call to get student
        Student student = studentService.getStudentById(id);

        //mapping model to dto
        GetStudentResponse studentDto = new GetStudentResponse(student.getId(), student.getName());


       return ResponseEntity.ok(studentDto);

    }

    //TODO: convert this method to return Page info
    // get student by age : GET http method
    @GetMapping("/age/{age}") 
    ResponseEntity<List<GetStudentResponse>> getStudentsByAge(@PathVariable int age){
        //service call to get students by age
        List<Student> studentsByAge = studentService.getStudentsByAge(age);

        //mapping model to dto
        List<GetStudentResponse> studentAgeDtos = studentsByAge.stream()
                .map((s)-> new GetStudentResponse(s.getId(), s.getName()) ).toList();

        return ResponseEntity.ok(studentAgeDtos);
    }

    // create student: POST http method
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping()
    ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest studentRequest) {
        // map CreateStudentRequest to Student
        Student student = new Student(studentRequest.name(), studentRequest.age(), studentRequest.password());

        // call student/id service createStudent() method with Student
        Student createdStudent = studentService.createStudent(student);

        // map student to CreateStudentResponse
        CreateStudentResponse studentDto = new CreateStudentResponse(createdStudent.getId());

        // return CreateStudentResponse as a http response
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto);
    }

    // update student
    @PatchMapping("/{id}")
    ResponseEntity<UpdateStudentResponse> getStudentByAge(@PathVariable Long id, @RequestBody UpdateStudentRequest studentRequest){
        //mapping updateRequest to student
        Student student = new Student(studentRequest.name(), studentRequest.age(), studentRequest.password());
        student.setId(id);

        //service call  to update student
        Student updateStudent = studentService.updateStudent(student);

        // map to dto
        UpdateStudentResponse updateStudentResponse = new UpdateStudentResponse(updateStudent.getId(), updateStudent.getName(), updateStudent.getAge());

        return ResponseEntity.ok(updateStudentResponse);
    }

    // delete student : DELETE http method
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        // call student service deleteStudent()
        studentService.deleteStudent(id);

        // return http request
        return ResponseEntity.noContent().build();
    }

}
