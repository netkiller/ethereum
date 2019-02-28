package api.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Certificate;

public interface CertificateRepository extends MongoRepository<Certificate, String> {

	Certificate findOneByMemberId(String memberId);

	Certificate findOneByAssetsId(String assetsId);

	List<Certificate> findByMemberId(String memberId);

	List<Certificate> findByAssetsId(String assetsId);

	Certificate findOneById(String id);

	List<Certificate> findByMemberIdAndStatus(String memberId, String status);

	Certificate findOneByAssetsIdAndMemberId(String assetsId, String memberId);

	List<Certificate> findByAssetsIdAndStatus(String assetsId, String status);

	List<Certificate> findByMemberIdAndStatus(String memberId, String status, PageRequest of);

}
