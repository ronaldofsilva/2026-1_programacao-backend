package br.ueg.posse.tsi.services;


import org.springframework.stereotype.Service;
import br.ueg.posse.tsi.models.People;
import br.ueg.posse.tsi.repositories.PeopleRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

   
    private final PeopleRepository repository;
    
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    // GET /people -> findAll() do MongoRepository
    public List<People> listarTodos() {
        return repository.findAll();
    }

    // GET /people/{id} -> findById() retorna Optional
    public Optional<People> buscarPorId(String id) {
        return repository.findById(id);
    }

    public People buscarPeoplePorId(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    // Busca por nome (derived query -> filtro no MongoDB)
    public List<People> buscarPorNome(String termo) {
        return repository.findByNameContainingIgnoreCase(termo);
    }

    // POST /people -> valida regras e salva no MongoDB
    public People cadastrar(People people) {
        if (people.getAge() < 0 || people.getAge() > 130)
            throw new IllegalArgumentException("Idade inválida");

        if (people.getName() == null || people.getName().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");

        if (repository.existsByEmail(people.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado");

        return repository.save(people); // MongoDB gera o _id automaticamente
    }

    // PUT /people/{id} -> verifica existência e atualiza
    public People atualizar(String id, People people) {
        People peopleUpdate = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pessoa não encontrada: " + id));
        peopleUpdate.setName(people.getName());
        peopleUpdate.setAge(people.getAge());
        peopleUpdate.setEmail(people.getEmail());

        return repository.save(peopleUpdate);
    }

    // DELETE /people/{id} -> verifica e remove
    public void deletar(String id) {
        if (!repository.existsById(id))
            throw new RuntimeException("People não encontrado: " + id);

        repository.deleteById(id);
    }

}