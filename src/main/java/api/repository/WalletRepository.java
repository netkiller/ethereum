package api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.domain.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Integer> {

	Wallet findOneByMemberId(String id);

	Wallet findOneByMnemonic(String mnemonic);

	Wallet findOneByPrivateKey(String privateKey);

}
