package br.ueg.posse.tsi.DTOs.people;

import java.time.LocalDateTime;

public class PeopleResponseDTO {
    private String id;
    private String name;
    private int age;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public PeopleResponseDTO(
        String id, String name, int age,
        String email, LocalDateTime createdAt,
        LocalDateTime updatedAt
    ){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt =  updatedAt;
    }
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
