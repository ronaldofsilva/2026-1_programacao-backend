package br.ueg.posse.tsi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ueg.posse.tsi.DTOs.people.PeopleCreateDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleUpdateDTO;
import br.ueg.posse.tsi.services.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "People", description = "Gerenciamento de pessoas")
@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService service;

    public PeopleController(PeopleService service) {
        this.service = service;
    }

    // GET /people -> 200 OK
    @GetMapping("/get")
    @Operation(summary = "Listar pessoas", description = "Retorna todas as pessoas cadastradas")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    public ResponseEntity<List<PeopleResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /people/{id} -> 200 OK ou 404 Not Found
    @Operation(summary = "Buscar pessoa por ID", description = "Localiza uma pessoa pelo ID passado por parâmetro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<PeopleResponseDTO> buscarPorId(
            @Parameter(description = "ID da pessoa", example = "6828f7a7a8123c001f11aa11") 
            @PathVariable String id) {
        return ResponseEntity.ok(service.buscarPessoaPorId(id));
    }

    // GET /people/name/{name} -> 200 OK ou 404 Not Found
    @Operation(summary = "Buscar pessoa por nome", description = "Localiza uma pessoa pelo nome passado por parâmetro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<PeopleResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome para localizar", example = "Maria dos Santos") @PathVariable String name) {
        List<PeopleResponseDTO> peoples = service.buscarPorNome(name);
        return ResponseEntity.ok(peoples);
    }

    // POST /people -> 201 Created
    @Operation(summary = "Cadastrar nova pessoa", description = "Cria um novo registro de pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/post")
    public ResponseEntity<PeopleResponseDTO> salvar(@RequestBody PeopleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    // PUT /people/{id} -> 200 OK ou 404 Not Found
    @Operation(summary = "Atualizar uma pessoa cadastrada", description = "Atualizar uma pessoa do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pessoa atualizada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PeopleResponseDTO> alterar(
            @Parameter(description = "ID da pessoa", example = "6828f7a7a8123c001f11aa11") 
            @PathVariable String id,
            @RequestBody(
                description = "Dados da pessoa",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PeopleUpdateDTO.class)
                )
            ) 
            PeopleUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE /people/{id} -> 204 No Content
    @Operation(summary = "Excluir pessoa", description = "Remove uma pessoa do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pessoa removida"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}