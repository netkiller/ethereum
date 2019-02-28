package api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {

	Profile findByMemberId(String memberId);

	Profile findOneById(String id);

	List<Profile> findAllByStatus(String status);

	Profile findOneByMemberId(String id);

}
