package com.example.quanlisinhvien.services;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.model.Student;
import com.example.quanlisinhvien.repository.StudentRepository;
import com.example.quanlisinhvien.repository.StudentsRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentServices {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentsRepository studentsRepositoryQuery;

    // create student
    public ResponseEntity<ResponseObject> addStudent(Student student) {
        List<Student> students = studentRepository.findStudentByName(student.getName().trim());
        if (students.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("error", "Student Name Already Taken", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Insert Student Successfully", studentRepository.save(student)));
    }

    // get all student
    public List<Student> getListStudent() {
        return studentRepository.findAll();
    }

    // get student by id
    public ResponseEntity<ResponseObject> getListStudentById(String id) {
        Optional<Student> students = studentRepository.findById(id);
        return students.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query student successfully", students)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("false", "Cannot find product with id = " + id, ""));
    }

    // update student
    public ResponseEntity<ResponseObject> updateStudent(Student newStudent, String id) {
        Student updateStd = studentRepository.findById(id).map(student1 -> {
            student1.setName(newStudent.getName());
            student1.setAge(newStudent.getAge());
            student1.setAddress(newStudent.getAddress());
            student1.setGender(newStudent.getGender());
            student1.setLike(newStudent.getLike());
            return studentRepository.save(student1);
        }).orElseGet(() -> {
            newStudent.setId(id);
            return studentRepository.save(newStudent);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update Student Successfully", updateStd));
    }

    public ResponseEntity<ResponseObject> removeStudent(String id) {
        boolean exists = studentRepository.existsById(id);
        if (exists) {
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Remove student successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot find product to delete", ""));


    }

    public ResponseEntity<ResponseObject> getAllStudentInPage(int pageNo, int pageSize, String sortBy) {
        Map<String, Object> response = new HashMap<>();
        Sort sort = Sort.by(sortBy);
        Pageable page = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> studentPage = studentRepository.findAll(page);

        response.put("data", studentPage.getContent());
        response.put("totalPage", studentPage.getTotalPages());
        response.put("totalElement", studentPage.getTotalElements());
        response.put("currenPageNo", studentPage.getNumber());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Insert Student Successfully", response));
    }

    // thử với query
    public ResponseEntity<ResponseObject> getAllStudents() {
        List<Object> students = studentsRepositoryQuery.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Get All student query", students));
    }


    public Object addStudentQuery(Map<String, Object> student) {
        Document doc = new Document(student);
        return studentsRepositoryQuery.addStudentQuery(doc);
    }

    public ResponseEntity<ResponseObject> updateStudentQuery(Map<String, Object> student) {
        Document doc = new Document(student);
        String id = doc.getString("id");
        try {
            doc.remove(id);
            return studentsRepositoryQuery.updateStudentQuery(doc, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Lỗi rồi", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseObject> removeStudentQuery(String id) {
        return studentsRepositoryQuery.removeStudentQuery(id);
    }


}

