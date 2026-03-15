package com.chameera.student.student;

import com.chameera.student.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Page<Student> getStudents(Pageable pageable) {
        //findByAll return a list of students [new Student(), ...]
        //if no students then it return empty array []
        return studentRepository.findAll(pageable);
    }

    public Student getStudentById(Long id) {
        //findById return a student or null(which is optional in java)
        //if optional then it throws StudentNotFoundException else it return Student
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

    }

    public List<Student> getStudentsByAge(int age) {
        //Option 1: Stream API (Less efficient but works)
        /*return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge().equals(age))
                .toList();*/

        //Option 2: Custom Repository Query (Recommended)
        return studentRepository.findByAge(age);
    }

    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        studentRepository.delete(student);
    }

    public Student updateStudent(Student student) {
        Student studentDb = studentRepository.findById(student.getId())
                .orElseThrow(() -> new StudentNotFoundException(student.getId()));

        if (student.getName() != null) studentDb.setName(student.getName());
        if (student.getAge() != null) studentDb.setAge(student.getAge());
        if (student.getPassword() != null) studentDb.setPassword(student.getPassword());

        return studentRepository.save(studentDb);
    }
}