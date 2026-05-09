package br.ueg.posse.tsi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.services.PeopleService;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService service;
    
    public PeopleController(PeopleService service) {
        this.service = service;
    }

    // GET /people -> 200 OK
    @GetMapping("/get")
    public ResponseEntity<List<People>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /people/{id} -> 200 OK ou 404 Not Found
    @GetMapping("/get/{id}")
    public ResponseEntity<People> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /people/name/{name} -> 200 OK ou 404 Not Found
    @GetMapping("/name/{name}")
    public ResponseEntity<List<People>> buscarPorNome(@PathVariable String name) {
        List<People> peoples = service.buscarPorNome(name);
        if (peoples.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(peoples);
    }

    // POST /people -> 201 Created
    @PostMapping("/post")
    public ResponseEntity<People> salvar(@RequestBody People people) {
        People newPeople = service.cadastrar(people);
        return ResponseEntity.status(201).body(newPeople);
    }

    // PUT /people/{id} -> 200 OK ou 404 Not Found
    @PutMapping("/update/{id}")
    public ResponseEntity<People> alterar(@PathVariable String id,  
            @RequestBody People people){
        //Exceção no controller
        //Podemos centrar as exceções em um único local?
        try {
            return ResponseEntity.ok(service.atualizar(id, people));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }

    // DELETE /people/{id} -> 204 No Content
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}