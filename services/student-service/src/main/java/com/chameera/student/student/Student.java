package com.chameera.student.student;

import com.chameera.student.auth.model.User;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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
