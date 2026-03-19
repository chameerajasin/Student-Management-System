package com.chameera.student.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    Optional<Student> findByUserId(Long userId);
    Optional<Student> findByUserUsername(String username);
}
