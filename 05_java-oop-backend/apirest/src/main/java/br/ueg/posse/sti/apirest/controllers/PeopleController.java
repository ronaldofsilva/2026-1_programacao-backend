package br.ueg.posse.sti.apirest.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.ueg.posse.sti.apirest.models.People;
import br.ueg.posse.sti.apirest.services.PeopleService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/people")
public class PeopleController {
    /**
     * Fazer os mapeamentos da rotas da API
     * Injetar o service e chamar os métodos implementados no service
     */
    
    /** 
     * Injeção de dependência pelo construtor (Constructor Injection)
     * Qual o melhor método? Automático ou pelo construtor?
    */
    private final PeopleService peopleService;
    
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<People> salvar(@RequestBody People people) {
        People newPeople = peopleService.save(people);
        return ResponseEntity.status(201).body(newPeople);
    }

}
