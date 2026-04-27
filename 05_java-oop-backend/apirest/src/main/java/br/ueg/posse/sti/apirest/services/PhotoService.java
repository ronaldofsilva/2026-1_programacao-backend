package br.ueg.posse.sti.apirest.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.ueg.posse.sti.apirest.models.Photo;
import br.ueg.posse.sti.apirest.repositories.PhotoRepository;

public class PhotoService {

    private PhotoRepository photoRepository;

    /*Define o caminho onde as fotos serão salvas 
    Em uma aplicação real deve ser informado o caminho absoluto do serviço de storage, por exemplo Amazon S3*/
    private final String uploadDirectory = "src/main/resources/static/uploads/";

    public PhotoService(PhotoRepository photoRepository){
        this.photoRepository = photoRepository;
    }

    /**
     * Esse métodos faz o upload da foto. No Java, a classe MultpartFile é utilizada para minpulação de arquivos
     * @param file
     * @param photoMetadata //Esse parâmetro deverá receber no controller o ID da pessoa para fazer a referência
     * @return
     * @throws IOException
     */
    public Photo uploadPhoto(MultipartFile file, Photo photoMetadata) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("O arquivo está vazio!");
        }
        /**
         * Importante validar a extensão o arquivo também, para evitar que o usuário envie arquivos maliciosos
         * como scripts .sh ou binários .exe
         */
        Path directoryPath = Paths.get(uploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        photoMetadata.setPhoto(fileName);
        return photoRepository.save(photoMetadata);
    }

     /**
     * Implementação dos métodos CRUD
     */



}
