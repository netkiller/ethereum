package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Logging;

public interface LoggingRepository extends MongoRepository<Logging, String> {

}
