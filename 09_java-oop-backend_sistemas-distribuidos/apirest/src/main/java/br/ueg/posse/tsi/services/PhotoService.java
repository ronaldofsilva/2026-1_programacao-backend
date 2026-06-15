package br.ueg.posse.tsi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.ueg.posse.tsi.DTOs.photo.PhotoCreateDTO;
import br.ueg.posse.tsi.DTOs.photo.PhotoResponseDTO;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.models.Photo;
import br.ueg.posse.tsi.repositories.PhotoRepository;
import reactor.core.publisher.Mono;

@Service
public class PhotoService {
    private final PhotoRepository repository;
    private final RestTemplate restTemplate;

    private static final String NODE_URL = "http://localhost:3000/upload";
    private final WebClient client = WebClient.create(NODE_URL);

    /*
     * Define o caminho onde as fotos serão salvas
     * Em uma aplicação real deve ser informado o caminho absoluto do serviço de
     * storage, por exemplo Amazon S3
     */
    private final String uploadDirectory = "apirest/src/main/resources/static/uploads/";

    public PhotoService(PhotoRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
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
    /*
    Essa implementação é reativa e não bloqueante, contudo, para funcionar todos os métodos devem
    ser não bloqueantes, incluindo os endpoints dos controllers
    public Mono<PhotoResponseDTO> uploadAndSave(
            MultipartFile file,
            PhotoCreateDTO dto) throws IOException {
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        return client.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(
                        BodyInserters.fromMultipartData("file", resource))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .map(resp -> {
                    dto.setPhoto((String) resp.get("filename"));
                    Photo photo = toEntity(dto);
                    Photo saved = repository.save(photo);
                    return toResponse(saved);
                });
    }
     */
    public PhotoResponseDTO uploadAndSave(
            MultipartFile file, PhotoCreateDTO dto) throws IOException {
        // Monta requisição multipart
        var body = new LinkedMultiValueMap<String, Object>();
        body.add("file", new ByteArrayResource(
                file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        // Chama o microsserviço Node.js
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        var resp = restTemplate.postForObject(
                NODE_URL, new HttpEntity<>(body, headers),
                Map.class);
        // Rest template retorna um objeto
        // Necessário fazer o cast para String pois a propriedade photo da classe Photo e
        // seus respectivos DTOs é um string
        // Corresponde ao nome do arquivo
        dto.setPhoto((String) resp.get("filename"));
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
