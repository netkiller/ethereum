package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Attribute;

public interface AttributeRepository extends MongoRepository<Attribute, String> {

	Attribute findOneByOrganizationId(String organizationId);

}