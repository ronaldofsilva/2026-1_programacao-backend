package br.ueg.posse.tsi.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.posse.tsi.DTOs.people.PeopleResponseDTO;
import br.ueg.posse.tsi.models.People;

@Repository
public interface PeopleRepository extends MongoRepository<People, String>{

    /**
     * Os métodos CRUD são automatimcanete criados
     * GET / POST/ UPDATE / DELETE / 
     * findById
     * @return
     */
    Optional<People> findByName(String name);
    List<People> findByAge(int age);
    List<PeopleResponseDTO> findByNameContainingIgnoreCase(String termo);
    boolean existsByEmail(String email);
    
    @Query("{'age': {$gte: ?0, $lte: ?1}}")
    List<People> findByAgeBetween(int min, int max);
    List<People> findByEmail(String email);
}
