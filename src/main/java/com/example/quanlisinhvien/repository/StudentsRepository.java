package com.example.quanlisinhvien.repository;

import com.example.quanlisinhvien.model.ResponseObject;
import com.example.quanlisinhvien.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentsRepository {
    @Autowired
    MongoClient mongoClient;


    public List<Object> getAllStudents() {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("student");
        FindIterable<Document> findIterable = collection.find();
        List<Object> studentsResponse = new ArrayList<Object>();
        for (Document doc : findIterable) {
            studentsResponse.add(doc);
        }
        return studentsResponse;
    }

    public ResponseEntity<ResponseObject> addStudentQuery(Document doc) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("student");
        try {
            collection.insertOne(doc);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Add student query", doc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Add student query error", e));

        }


    }

    public ResponseEntity<ResponseObject> updateStudentQuery(Document doc, String id) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("student");

        BasicDBObject filter = new BasicDBObject("_id", id);
        BasicDBObject update = new BasicDBObject("$set", doc);

        try {
            collection.updateOne(filter, update);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Update query thành công ", doc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Update query thất bại ", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseObject> removeStudentQuery(String id) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("student");

        BasicDBObject filter = new BasicDBObject("_id", id);
        try {
            collection.deleteOne(filter);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Remove query thành công ", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Remove query thất bại ", e.getMessage()));
        }

    }


}
