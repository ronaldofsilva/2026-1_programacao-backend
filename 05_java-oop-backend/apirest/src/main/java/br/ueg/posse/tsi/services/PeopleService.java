package br.ueg.posse.tsi.services;

import org.springframework.stereotype.Service;

import br.ueg.posse.tsi.DTOs.people.PeopleCreateDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleUpdateDTO;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.repositories.PeopleRepository;
import java.util.List;

@Service
public class PeopleService {

    private final PeopleRepository repository;
    //Um dos principios SOLID - D
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
        ///como se fosse this.repository = new PeopleRepository()
    }

    // GET /people -> findAll() do MongoRepository
    public List<PeopleResponseDTO> listarTodos() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public PeopleResponseDTO buscarPessoaPorId(String id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() 
                -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    // Busca por nome (derived query -> filtro no MongoDB)
    public List<PeopleResponseDTO> buscarPorNome(String termo) {
        return repository.findByNameContainingIgnoreCase(termo);
    }

    // POST /people -> valida regras e salva no MongoDB
    public PeopleResponseDTO cadastrar(PeopleCreateDTO dto) {
        if (dto.getAge() < 0 || dto.getAge() > 130)
            throw new IllegalArgumentException("Idade inválida");
        if (dto.getName() == null || dto.getName().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (repository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado");
        return toResponse(repository.save(toEntity(dto))); // MongoDB gera o _id automaticamente
    }

    // PUT /people/{id} -> verifica existência e atualiza
    public PeopleResponseDTO atualizar(String id, PeopleUpdateDTO dto) {
        People entity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pessoa não encontrada: " + id));         
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        return toResponse(repository.save(entity));
    }
    // DELETE /people/{id} -> verifica e remove
    public void deletar(String id) {
        if (!repository.existsById(id))
            throw new RuntimeException("People não encontrado: " + id);
        repository.deleteById(id);
    }

    private PeopleResponseDTO toResponse(People p){
        return new PeopleResponseDTO(
            p.getId(), p.getName(), p.getAge(),
            p.getEmail(), p.getCreatedAt(), 
            p.getUpdatedAt()
        );
    };

    private People toEntity(PeopleCreateDTO dto){
        return new People(
            dto.getName(),
            dto.getAge(),
            dto.getEmail());
    };

}