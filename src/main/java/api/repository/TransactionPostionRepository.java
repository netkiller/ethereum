package api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.domain.TransactionPostion;

@Repository
public interface TransactionPostionRepository extends CrudRepository<TransactionPostion, Integer> {

	TransactionPostion findOneByAddress(String address);

}
