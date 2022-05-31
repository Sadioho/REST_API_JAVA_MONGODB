package com.example.quanlisinhvien.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Data
@Document
public class User {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private Number age;
    private Timestamp dateOfBirth;
    private List<String> language;
    private List<String> nationality;
    private List<String> address;
    public User() {
        super();
    }
    public User(String name, Number age, Timestamp dateOfBirth, List<String> language, List<String> nationality, List<String> address) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.language = language;
        this.nationality = nationality;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getAge() {
        return age;
    }

    public void setAge(Number age) {
        this.age = age;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getNationality() {
        return nationality;
    }

    public void setNationality(List<String> nationality) {
        this.nationality = nationality;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

}
