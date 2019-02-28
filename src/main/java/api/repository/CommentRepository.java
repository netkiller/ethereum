package api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findAllByAssetsId(String organizationId);

	Comment findOneById(String id);

}