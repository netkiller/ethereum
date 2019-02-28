package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Hypermedia;

public interface HypermediaRepository extends MongoRepository<Hypermedia, String> {

}
