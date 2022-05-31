package com.example.quanlisinhvien.repository;

import com.example.quanlisinhvien.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findStudentByName(String name);
}
