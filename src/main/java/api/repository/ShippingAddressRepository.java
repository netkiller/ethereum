package api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.ShippingAddress;


public interface ShippingAddressRepository extends MongoRepository<ShippingAddress, String> {

	ShippingAddress findOneByMemberId(String memberId);

	List<ShippingAddress> findAllByMemberId(String memberId);

	ShippingAddress findOneById(String id);

	List<ShippingAddress> findAllByMemberIdOrderByDefaultsDesc(String memberId);
}
