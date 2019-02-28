package api.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Employees;
import api.pojo.Enum.Status;

public interface EmployeesRepository extends MongoRepository<Employees, String> {

	List<Employees> findAllByOrganizationId(String organizationId, PageRequest of);

	List<Employees> findAllByOrganizationIdAndStatus(String organizationId, String status, PageRequest of);

	List<Employees> findByMemberId(String memberId);

	Employees findOneById(String id);

	List<Employees> findAllByStatus(String status);

	Employees findOneByMemberId(String memberId);

	List<Employees> findAllById(String id);

	List<Employees> findAllByOrganizationIdAndStatus(String organizationId, Status enabled);

}
