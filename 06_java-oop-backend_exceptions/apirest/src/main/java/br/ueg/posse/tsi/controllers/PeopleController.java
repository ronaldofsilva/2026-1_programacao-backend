package br.ueg.posse.tsi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ueg.posse.tsi.DTOs.people.PeopleCreateDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleUpdateDTO;
import br.ueg.posse.tsi.services.PeopleService;

@RestController
@RequestMapping("people")
public class PeopleController {

    private final PeopleService service;

    public PeopleController(PeopleService service) {
        this.service = service;
    }

    // GET /people -> 200 OK
    @GetMapping("/get")
    
    public ResponseEntity<List<PeopleResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /people/{id} -> 200 OK ou 404 Not Found
    @GetMapping("/get/{id}")
    public ResponseEntity<PeopleResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPessoaPorId(id));
    }

    // GET /people/name/{name} -> 200 OK ou 404 Not Found
    @GetMapping("/name/{name}")
    public ResponseEntity<List<PeopleResponseDTO>> buscarPorNome(@PathVariable String name) {
        List<PeopleResponseDTO> peoples = service.buscarPorNome(name);
        return ResponseEntity.ok(peoples);
    }

    // POST /people -> 201 Created
    @PostMapping("/post")
    public ResponseEntity<PeopleResponseDTO> salvar(@RequestBody PeopleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    // PUT /people/{id} -> 200 OK ou 404 Not Found
    @PutMapping("/update/{id}")
    public ResponseEntity<PeopleResponseDTO> alterar(@PathVariable String id,
            @RequestBody PeopleUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE /people/{id} -> 204 No Content
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}