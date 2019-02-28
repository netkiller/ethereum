package api.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Employees extends Profile {

	private String organizationId; // 隶属机构

	public Employees() {
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "Employees [organizationId=" + organizationId + "] => " + super.toString();
	}

}
