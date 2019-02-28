package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import api.domain.Member;

public interface MemberRepository extends MongoRepository<Member, String> {

	Member findOneById(String id);

	Member findOneByMobile(String mobile);

	boolean existsByMobile(String mobile);

	Member findOneByWechat(String wechat);

}
