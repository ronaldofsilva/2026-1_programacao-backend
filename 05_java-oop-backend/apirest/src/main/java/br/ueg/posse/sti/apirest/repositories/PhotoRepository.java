package br.ueg.posse.sti.apirest.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ueg.posse.sti.apirest.models.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String>{
    
}
