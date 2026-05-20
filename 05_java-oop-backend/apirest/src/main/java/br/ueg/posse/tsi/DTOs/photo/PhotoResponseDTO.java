package br.ueg.posse.tsi.DTOs.photo;

import java.time.LocalDateTime;


import br.ueg.posse.tsi.models.People;

public class PhotoResponseDTO {
  
    private String id;
    private People people;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
    public PhotoResponseDTO(
        String id, People people, String photo,
        LocalDateTime createdAt, LocalDateTime updatedAt
    ){
        this.id = id;
        this.people = people;
        this.photo = photo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public People getPeople() {
        return people;
    }
    public void setPeople(People people) {
        this.people = people;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
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
