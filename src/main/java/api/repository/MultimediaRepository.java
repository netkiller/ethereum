package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Multimedia;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {

}
