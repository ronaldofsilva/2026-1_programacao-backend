package br.ueg.posse.tsi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.ueg.posse.tsi.DTOs.photo.PhotoCreateDTO;
import br.ueg.posse.tsi.DTOs.photo.PhotoResponseDTO;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.models.Photo;
import br.ueg.posse.tsi.repositories.PhotoRepository;

@Service
public class PhotoService {
    private final PhotoRepository repository;

    /*
     * Define o caminho onde as fotos serão salvas
     * Em uma aplicação real deve ser informado o caminho absoluto do serviço de
     * storage, por exemplo Amazon S3
     */
    private final String uploadDirectory = "apirest/src/main/resources/static/uploads/";

    public PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    /**
     * Esse método faz o upload da foto. No Java, a classe MultpartFile é utilizada
     * para minpulação de arquivos
     * 
     * @param file
     * @param photoMetadata //Esse parâmetro deverá receber no controller o ID da
     *                      pessoa para fazer a referência
     * @return
     * @throws IOException
     */

    public List<PhotoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<PhotoResponseDTO> buscarPorId(String id) {
        return repository.findById(id)
                .map(this::toResponse);
    }

    public PhotoResponseDTO uploadAndSave(MultipartFile file, PhotoCreateDTO dto) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("O arquivo está vazio!");
        }
        if (dto.getPeople() == null && dto.getPeople().getId() == null) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        /**
         * Importante validar a extensão o arquivo também, para evitar que o usuário
         * envie arquivos maliciosos
         * como scripts .sh ou binários .exe
         */
        Path directoryPath = Paths.get(uploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        dto.setPhoto(fileName);
        return toResponse(repository.save(toEntity(dto)));
    }

    public Photo replaceFile(String id, MultipartFile newFile) throws IOException {
        Photo existingPhoto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada com o ID: " + id));
        if (newFile.isEmpty()) {
            throw new RuntimeException("O novo arquivo está vazio!");
        }
        String oldFileName = existingPhoto.getPhoto();
        if (oldFileName != null && !oldFileName.isEmpty()) {
            Path oldFilePath = Paths.get(uploadDirectory).resolve(oldFileName);
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                System.err.println("Erro ao deletar arquivo antigo: " + e.getMessage());
            }
        }
        Path directoryPath = Paths.get(uploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        String newFileName = UUID.randomUUID().toString() + "_" + newFile.getOriginalFilename();
        Path newFilePath = directoryPath.resolve(newFileName);
        Files.copy(newFile.getInputStream(), newFilePath);
        existingPhoto.setPhoto(newFileName);
        return repository.save(existingPhoto);
    }

    public List<PhotoResponseDTO> buscarPorPessoa(String peopleId) {
        People people = new People();
        people.setId(peopleId);

        if (peopleId == null || peopleId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "ID da pessoa não pode ser vazio");
        }

        return repository.findByPeople(people)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deletePhoto(String id) throws IOException {
        Photo photo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada com o ID: " + id));
        deleteFile(photo.getPhoto());
        repository.deleteById(id);
    }

    private void deleteFile(String fileName) throws IOException {
        if (fileName != null && !fileName.isEmpty()) {
            Path filePath = Paths.get(uploadDirectory).resolve(fileName);
            // Verificar se o arquivo existe antes de tentar deletar
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Photo deletada com sucesso: " + fileName);
            } else {
                System.out.println("Photo não encontrada no disco: " + fileName);
            }
        }
    }

    private PhotoResponseDTO toResponse(Photo p) {
        return new PhotoResponseDTO(
                p.getId(), p.getPeople(), p.getPhoto(),
                p.getCreatedAt(),
                p.getUpdatedAt());
    };

    private Photo toEntity(PhotoCreateDTO dto) {
        return new Photo(
                dto.getPeople(),
                dto.getPhoto());
    };

}
