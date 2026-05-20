package br.ueg.posse.tsi.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


// Padrão Data Mapper + Active Record
// Separa a camada de domínio (objetos de negócio) da camada de banco de dados, utilizando mapeadores intermediários
// por meio de anotações
@Document(collection = "people")
public class People {
    @Id
    private String id;
    private String name;
   private int age;
    private String email;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public People(){
        
    }

    public People(String name, int age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public People(String id, String name, int age, String email){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
