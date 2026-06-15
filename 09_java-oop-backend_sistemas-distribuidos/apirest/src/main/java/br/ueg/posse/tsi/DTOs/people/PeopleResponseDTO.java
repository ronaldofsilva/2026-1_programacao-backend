package br.ueg.posse.tsi.DTOs.people;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta da pessoa")
public class PeopleResponseDTO {
    @Schema(description = "Identificador único", type = "String", example = "6828f7a7a8123c001f11aa11")
    private String id;
    @Schema(description = "Nome completo", type = "String", example = "Ronaldo Ferreira Silva")
    private String name;
    @Schema(description = "Idade", type = "int", example = "35")
    private int age;
    @Schema(description = "Email", example = "ronaldo@email.com")
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
