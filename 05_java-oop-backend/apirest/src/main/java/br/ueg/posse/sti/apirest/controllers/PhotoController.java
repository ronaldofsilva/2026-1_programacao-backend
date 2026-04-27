package br.ueg.posse.sti.apirest.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.ueg.posse.sti.apirest.models.Photo;
import br.ueg.posse.sti.apirest.services.PhotoService;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    /**
     * Fazer os mapeamentos da rotas da API
     * Injetar o service e chamar os métodos implementados no service
     */
    @Autowired /*
                * Esta é uma injeção de dependência automática. O SpringBoot cuida disso por
                * meio da anotação @Autowired.
                * Analise a injeção de dependência automatica na classe PeopleController
                */
    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<Photo> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("peopleId") String peopleId) {

        try {
            Photo photo = new Photo();
            //Aqui deve fazer a busca da pessoa pelo ID e setar no objeto
            //peopleId
            //photo.setPleople(peopleService.findById(peopleId));

            Photo savedPhoto = photoService.uploadPhoto(file, photo);
            return ResponseEntity.ok(savedPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

}
