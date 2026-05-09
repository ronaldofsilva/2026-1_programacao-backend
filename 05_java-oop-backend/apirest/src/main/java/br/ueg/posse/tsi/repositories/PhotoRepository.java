package br.ueg.posse.tsi.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.tsi.models.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String>{
    List<Photo> findByPeople(String peopleId);
}
