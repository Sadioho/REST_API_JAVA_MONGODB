package com.example.quanlisinhvien.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Document
public class Student {
    @Id
    private String id;

    private String name;
    private Integer age;
    private List<String> like;
    private Address address;
    private Gender gender;
    public Student(){
        super();
    }
    public Student(String name, Integer age, List<String> like, Address address, Gender gender) {
        this.name = name;
        this.age = age;
        this.like = like;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
