package com.example.quanlisinhvien.controller;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.model.Student;
import com.example.quanlisinhvien.model.User;
import com.example.quanlisinhvien.services.StudentServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/student")
@AllArgsConstructor
public class StudentController {
    @Autowired
    StudentServices studentServices;

    // get all student
    @GetMapping
    public List<Student> getListStudent() {
        return studentServices.getListStudent();
    }

    // get student by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getListStudentById(@PathVariable String id) {
        System.out.println(id);
        return studentServices.getListStudentById(id);
    }

    // create student
    @PostMapping
    public ResponseEntity<ResponseObject> addStudent(@RequestBody Student student) {
        return studentServices.addStudent(student);
    }

    // create student
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateStudent(@RequestBody Student student, @PathVariable String id) {
        return studentServices.updateStudent(student, id);
    }

    // create student
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> removeStudent(@PathVariable String id) {
        return studentServices.removeStudent(id);
    }

    //     size số lượng phần tử mỗi page,
    //     pageno là trang số bao nhiêu
    //     sort theo id
    @GetMapping("/page")
    public ResponseEntity<ResponseObject> getAllStudentInPage(
            @RequestParam(name = "pageno", defaultValue = "0") int pageNo,
            @RequestParam(name = "pagesize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sortby", defaultValue = "id") String sortBy
    ) {
        return studentServices.getAllStudentInPage(pageNo, pageSize, sortBy);
    }

    // thử xử dụng query
    @GetMapping("/query")
    public ResponseEntity<ResponseObject> getAllStudents() {
        return studentServices.getAllStudents();
    }

    @PostMapping("/query")
    public Object addStudentQuery(@RequestBody Map<String, Object> student) {
        return studentServices.addStudentQuery(student);
    }

    @PutMapping("/query")
    public Object updateStudentQuery(@RequestBody Map<String, Object> student) {
        return studentServices.updateStudentQuery(student);
    }

//    @DeleteMapping("/query/{id}")
//    public ResponseEntity<ResponseObject> removeStudentQuery(@RequestParam String id) {
//        return studentServices.removeStudentQuery(id);
//    }



}
