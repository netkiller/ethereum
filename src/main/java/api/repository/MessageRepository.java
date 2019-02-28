package api.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

	List<Message> findAllByMemberId(String memberId, PageRequest of);

	List<Message> findAllByMemberIdAndStatus(String memberId, String status, PageRequest of);

	Message findOneById(String id);

}
