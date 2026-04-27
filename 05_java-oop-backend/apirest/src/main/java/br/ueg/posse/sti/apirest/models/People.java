package br.ueg.posse.sti.apirest.models;

import java.util.Calendar;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "people")
public class People {
    @Id
    private String id;
    private String name;
    private int age;
    @CreatedDate
    private Calendar createdAt;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
     public Calendar getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }
}
