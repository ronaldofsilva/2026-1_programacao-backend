package br.ueg.posse.tsi.services;


import org.springframework.stereotype.Service;

import br.ueg.posse.tsi.DTOs.people.PeopleCreateDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.DTOs.people.PeopleUpdateDTO;
import br.ueg.posse.tsi.exceptions.BusinessException;
import br.ueg.posse.tsi.exceptions.PeopleAlreadyExistsException;
import br.ueg.posse.tsi.exceptions.PeopleNotFoundException;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.repositories.PeopleRepository;
import java.util.List;

@Service
public class PeopleService {

    private final PeopleRepository repository; 
    //Um dos principios SOLID - D
    //Omitimos o @Autorited porque o Spring Boot o adiciona automaticamente
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
                -> new PeopleNotFoundException(id));
    }

    // Busca por nome (derived query -> filtro no MongoDB)
    public List<PeopleResponseDTO> buscarPorNome(String termo) {
        return repository.findByNameContainingIgnoreCase(termo);
    }

    // POST /people -> valida regras e salva no MongoDB
    public PeopleResponseDTO cadastrar(PeopleCreateDTO dto) {
        //Validação de regras de negócio: Idade válidade 0 - 130
        if (dto.getAge() < 0 || dto.getAge() > 130)
            throw new BusinessException(dto.getAge() + " é uma idade inválida");
        //Validação de regras de negócio: E-mail duplicado
        if (!repository.existsByEmail(dto.getEmail()))
            throw new PeopleAlreadyExistsException(dto.getEmail());
        return toResponse(repository.save(toEntity(dto))); // MongoDB gera o _id automaticamente
    }

    // PUT /people/{id} -> verifica existência e atualiza
    public PeopleResponseDTO atualizar(String id, PeopleUpdateDTO dto) {
        People entity = repository.findById(id)
                .orElseThrow(() ->
                        new PeopleNotFoundException("Pessoa não encontrada: " + id));         
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        return toResponse(repository.save(entity));
    }
    // DELETE /people/{id} -> verifica e remove
    public void deletar(String id) {
        if (!repository.existsById(id))
            throw new PeopleNotFoundException("People não encontrado: " + id);
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