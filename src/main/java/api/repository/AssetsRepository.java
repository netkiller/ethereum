package api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Assets;
import api.pojo.Enum.Examine;

public interface AssetsRepository extends MongoRepository<Assets, String> {

	List<Assets> findAllByOrganizationId(String organizationId, PageRequest of);

	List<Assets> findAllByOrganizationIdAndStatus(String organizationId, String status, PageRequest of);

	Assets findOneById(String id);

	Assets findOneByTransactionId(String transactionId);

	Assets findOneByUuid(String uuid);

	List<Assets> findAllByStatus(Examine status, PageRequest of);

	Assets findOneByQrcode(String qrcode);

	List<Assets> findAllByIdIn(List<String> assetsIds);

	List<Assets> findAllByUpdateDateBefore(Date yesterday);

	List<Assets> findAllByUpdateDateBeforeAndStatus(Date yesterday, Examine status);

}
