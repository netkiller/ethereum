package api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.domain.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

	Token findOneByContractAddress(String contractAddress);

}
