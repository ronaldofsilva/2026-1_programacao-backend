package br.ueg.posse.sti.apirest.services;

import br.ueg.posse.sti.apirest.models.People;
import br.ueg.posse.sti.apirest.repositories.PeopleRepository;

public class PeopleService {
    private PeopleRepository repository;

    /**
     * 
     * @param repository
     * Injeta o respositório no service. Injeção via construtor (Constructor Injection)
     */
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    /**
     * Demais operações CRUD
     * Lembrando que os métodos são implementados automaticamente pelo MongoRepository (MongoTemplate)
     */

    /**
     * Quais são os métodos
     * findAll()
     * findById(id)
     * findByNameContainingIgnoreCase(name)
     * save(pessoa)
     * deleteById(id)
     */

    
    public People save(People people){
        return repository.save(people);
    }

}
