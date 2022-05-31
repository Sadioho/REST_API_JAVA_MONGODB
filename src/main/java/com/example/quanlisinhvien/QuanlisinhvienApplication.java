package com.example.quanlisinhvien;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.model.Student;
import com.example.quanlisinhvien.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootApplication
public class QuanlisinhvienApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanlisinhvienApplication.class, args);
    }

    private ResponseEntity<ResponseObject> usingMongoQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String name, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("error", "Student Name Already Taken", ""));
        }
        if (students.isEmpty()) {
            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Student success", student));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("error", "Trung", student));
        }
    };
}
