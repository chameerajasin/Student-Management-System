package com.chameera.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data //getters and setter
@AllArgsConstructor //
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String password;

    public Student(String name, Integer age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public  Student(String name, Integer age){
        this.name= name;
        this.age = age;
    }

}
