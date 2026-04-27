package br.ueg.posse.sti.apirest.models;

import java.util.Calendar;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photo")
public class Photo {
    @Id
    private String id;

    @DBRef //Faz a referência à pessoa à qual a foto pertence. 
    private People people;
    private String photo;
    @CreatedDate
    private Calendar createdAt;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public People getPeople() {
        return people;
    }
    public void setPleople(People people) {
        this.people = people;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Calendar getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

}
