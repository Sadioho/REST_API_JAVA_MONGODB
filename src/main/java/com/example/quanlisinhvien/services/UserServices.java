package com.example.quanlisinhvien.services;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.repository.UserRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<ResponseObject> addUser(Map<String, Object> user) {
        Document doc = new Document(user);
        return userRepository.addUser(doc);
    }

    public ResponseEntity<ResponseObject> getAllUser() {
        return userRepository.getAllUser();
    }

    public ResponseEntity<ResponseObject> updateUser(Map<String, Object> user) {
        Document dataUser = new Document(user);
        String id = dataUser.getString("_id");
        ObjectId _id = new ObjectId(id);
        dataUser.remove("_id");
        return userRepository.updateUser(dataUser, _id);
    }

    public ResponseEntity<ResponseObject> findByLanguage(String language) {
        return userRepository.findByLanguage(language);
    }


    public ResponseEntity<ResponseObject> findByYearOfBirth(String yearOfBirth) {
        return userRepository.findByYearOfBirth(yearOfBirth);
    }
}
