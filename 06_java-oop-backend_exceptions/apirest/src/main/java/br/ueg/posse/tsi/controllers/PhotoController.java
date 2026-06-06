package br.ueg.posse.tsi.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.DTOs.photo.PhotoCreateDTO;
import br.ueg.posse.tsi.DTOs.photo.PhotoResponseDTO;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.models.Photo;
import br.ueg.posse.tsi.services.PeopleService;
import br.ueg.posse.tsi.services.PhotoService;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService service;

    @Autowired
    private PeopleService peopleService;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public ResponseEntity<List<PhotoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Observe o objeto passado no genérico "?"
    @PostMapping(value = "/post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> salvar(
            @RequestParam("photo") MultipartFile file,
            @RequestParam("peopleId") String peopleId) {
        try {
            // Photo photoMetadata = objectMapper.readValue(metadataJson, Photo.class);
            PeopleResponseDTO dto = peopleService.buscarPessoaPorId(peopleId);
            // Criar entidade Photo
            PhotoCreateDTO photoMetadata = new PhotoCreateDTO();
            // Photo espera uma entidade People, por isso não utilizamos o DTO
            People people = new People(
                    dto.getId(),
                    dto.getName(),
                    dto.getAge(),
                    dto.getEmail());
            photoMetadata.setPeople(people);
            PhotoResponseDTO newPhoto = service.uploadAndSave(file, photoMetadata);

            return ResponseEntity.status(HttpStatus.CREATED).body(newPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> alterar(
            @PathVariable String id,
            @RequestParam("photo") MultipartFile file) {
        try {
            Photo updatedPhoto = service.replaceFile(id, file);
            return ResponseEntity.ok(updatedPhoto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "NOT_FOUND",
                            "message", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "INTERNAL_ERROR",
                            "message", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> excluir(@PathVariable String id) {
        try {
            service.deletePhoto(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Foto excluída com sucesso",
                    "id", id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "NOT_FOUND",
                            "message", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "INTERNAL_ERROR",
                            "message", "Erro ao excluir o arquivo físico: " + e.getMessage()));
        }
    }

    @GetMapping("/people/{peopleId}")
    public ResponseEntity<List<PhotoResponseDTO>> buscarPorPessoa(@PathVariable String peopleId) {
        List<PhotoResponseDTO> photos = service.buscarPorPessoa(peopleId);
        return ResponseEntity.ok(photos);

    }
}
