package com.example.quanlisinhvien.controller;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    UserServices userServices;

    @GetMapping
    public ResponseEntity<ResponseObject> getUser() {
        return userServices.getAllUser();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addUser(@RequestBody Map<String, Object> user) {
        return userServices.addUser(user);
    }

    @PutMapping
    public ResponseEntity<ResponseObject> updateUser(@RequestBody Map<String, Object> user) {
        return userServices.updateUser(user);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseObject> findByLanguage(@RequestParam(name = "language") String language
    ) {
        return userServices.findByLanguage(language);
    }

    @GetMapping("/findYear")
    public ResponseEntity<ResponseObject> findByYearOfBirth(@RequestParam(name = "yearOfBirth") String yearOfBirth
    ) {
        return userServices.findByYearOfBirth(yearOfBirth);
    }


}
