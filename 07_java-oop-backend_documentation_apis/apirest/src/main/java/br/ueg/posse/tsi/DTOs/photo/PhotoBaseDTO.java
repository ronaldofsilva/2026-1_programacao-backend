package br.ueg.posse.tsi.DTOs.photo;

import br.ueg.posse.tsi.models.People;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PhotoBaseDTO {
    @NotNull(message = "Pessoa é obrigatório")
    private People people;
    @NotNull(message = "Arquivo é obrigatório")
    @Size(min = 1, message = "O caminho ou nome do arquivo deve ter pelo menos 1 caractere")
    private String photo;

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
}
