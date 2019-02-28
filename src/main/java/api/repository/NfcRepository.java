package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Nfc;

public interface NfcRepository extends MongoRepository<Nfc, String> {

	Nfc findOneById(String id);

}
