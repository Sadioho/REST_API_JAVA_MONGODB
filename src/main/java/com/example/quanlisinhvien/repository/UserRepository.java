package com.example.quanlisinhvien.repository;

import com.example.quanlisinhvien.model.ResponseObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    MongoClient mongoClient;
    private ObjectId ifd;

    public ResponseEntity<ResponseObject> addUser(Document doc) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("user");
        try {
            DateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
            Date formatStringToDate = stringToDate.parse((String) doc.get("dateOfBirth"));
            String dateOfBirth = stringToDateFormat.format(formatStringToDate);
            doc.replace("dateOfBirth", dateOfBirth);
            doc.append("yearBirth",getYear.format(formatStringToDate));
            collection.insertOne(doc);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "ADD USER SUCCESS", doc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "ADD USER FAILED", e.getMessage()));
        }


    }

    public ResponseEntity<ResponseObject> getAllUser() {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("user");
        FindIterable<Document> findIterable = collection.find();
        List<Object> userRes = new ArrayList<Object>();
        for (Document doc : findIterable) {
            userRes.add(doc);
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "GET ALL USER SUCCESS", userRes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "GET ALL USER FAILED", e.getMessage()));

        }

    }

    public ResponseEntity<ResponseObject> updateUser(Document dataUser, ObjectId _id) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("user");

        BasicDBObject filter = new BasicDBObject("_id", _id);
        BasicDBObject update = new BasicDBObject("$set", dataUser);
        try {
            collection.updateOne(filter, update);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "UPDATE USER SUCCESS", dataUser));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "UPDATE USER FAILED", e.getMessage()));

        }
    }

    public ResponseEntity<ResponseObject> findByLanguage(String language) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("user");
        BasicDBObject lang = new BasicDBObject("language", language);
        BasicDBObject match = new BasicDBObject("$match", lang);
        BasicDBObject unwind = new BasicDBObject("$unwind", "$language");

        BasicDBObject _idGroup = new BasicDBObject("language", "$language");
        BasicDBObject sumGroup = new BasicDBObject("$sum", 1);
        BasicDBObject itemsGroup = new BasicDBObject("$push", "$$ROOT");

        BasicDBObject grp = new BasicDBObject();
        grp.append("_id", _idGroup);
        grp.append("sum", sumGroup);
        grp.append("items", itemsGroup);
        BasicDBObject group = new BasicDBObject("$group", grp);

        BasicDBObject projects = new BasicDBObject();
        projects.append("_id", 0);
        projects.append("sum", 1);
        projects.append("language", "$_id.language");
        projects.append("items", 1);
        BasicDBObject project = new BasicDBObject("$project", projects);

        List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
        pipeline.add(match);
        pipeline.add(unwind);
        pipeline.add(group);
        pipeline.add(project);
        AggregateIterable<Document> findIterable = collection.aggregate(pipeline);
        List<Object> dataFind = new ArrayList<Object>();
        for (Document doc : findIterable) {
            dataFind.add(doc);
        }
        try {
            if (dataFind.size() > 0) {
                BasicDBObject count = new BasicDBObject("countLanguage", dataFind.size());
                dataFind.add(count);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "FIND USER SUCCESS", dataFind));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "FIND USER FAILED", "Không tìm thấy dữ liệu"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "FIND USER FAILED", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseObject> findByYearOfBirth(String yearOfBirth) {
        MongoDatabase database = mongoClient.getDatabase("student");
        MongoCollection<Document> collection = database.getCollection("user");
        // xử lí ngày
        try {
            DateFormat stringToDate = new SimpleDateFormat("yyyy");
            // query
            //match
            BasicDBObject eq = new BasicDBObject("$eq", yearOfBirth);
            BasicDBObject match = new BasicDBObject();
            match.append("yearBirth", eq);
            BasicDBObject matchPipe = new BasicDBObject("$match", match);
            // group
            BasicDBObject _idGroup = new BasicDBObject("dateOfBirth", "$dateOfBirth");
            BasicDBObject yearBirthGroup = new BasicDBObject("$first", "$dateOfBirth");
            BasicDBObject countGroup = new BasicDBObject("$sum", 1);
            BasicDBObject itemsGroup = new BasicDBObject("$push", "$$ROOT");
            BasicDBObject group = new BasicDBObject();
            group.append("_id", _idGroup);
            group.append("yearBirth", yearBirthGroup);
            group.append("count", countGroup);
            group.append("items", itemsGroup);
            BasicDBObject groupPipe = new BasicDBObject("$group", group);

            // project
            BasicDBObject dateToString = new BasicDBObject();
            dateToString.append("dateString", "$yearBirth");

            BasicDBObject yearBirthProject = new BasicDBObject();
            yearBirthProject.append("$dateFromString", dateToString);

            BasicDBObject projects = new BasicDBObject();
            projects.append("_id", 0);
            projects.append("items", 1);
            projects.append("count", 1);
            projects.append("yearBirth", yearBirthProject);
            BasicDBObject projectsPipe = new BasicDBObject("$project", projects);

            List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
            pipeline.add(matchPipe);
            pipeline.add(groupPipe);
            pipeline.add(projectsPipe);
            FindIterable<Document> findIterableAll = collection.find();
            AggregateIterable<Document> findIterable = collection.aggregate(pipeline);
            List<Object> dataFind = new ArrayList<Object>();
            List<Object> dataFindAll = new ArrayList<Object>();
            for (Document doc : findIterable) {
                doc.replace("yearBirth",stringToDate.format(doc.getDate("yearBirth")));
                dataFind.add(doc);
            }
            for (Document doc : findIterableAll) {
                dataFindAll.add(doc);
            }
            if (dataFind.size() > 0 && dataFindAll.size() > 0) {
                BasicDBObject count = new BasicDBObject("total", dataFindAll.size());
                dataFind.add(count);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "FIND USER SUCCESS", dataFind));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "FIND USER FAILED", "Không tìm thấy dữ liệu"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "FIND USER FAILED", e.getMessage()));

        }
    }
}
