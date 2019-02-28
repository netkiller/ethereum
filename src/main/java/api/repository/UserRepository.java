package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api.domain.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByUsername(String username);

}
