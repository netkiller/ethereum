package api.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Organization;

public interface OrganizationRepository extends MongoRepository<Organization, String> {

	Organization findOneByMemberId(String memberId);

	List<Organization> findAllByStatus(String status, PageRequest of);

	Organization findOneById(String id);
}
