package br.ueg.posse.sti.apirest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.ueg.posse.sti.apirest.models.People;

public interface PeopleRepository extends MongoRepository<People, String>  {
    Optional<People> findByName(String name);
    List<People> findByAge(int age);
    List<People> findByAgeGreaterThan(int minAge);
    @Query("{'age':{$gte?0, $lte:?1 }}")
    List<People> findByAgeBetween(int min, int max); 
}
