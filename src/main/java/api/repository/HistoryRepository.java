package api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.History;

public interface HistoryRepository extends MongoRepository<History, String> {

	List<History> findByAssetsId(String assetsId);

}
