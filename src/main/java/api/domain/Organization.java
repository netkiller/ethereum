package api.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import api.pojo.Enum.Status;

@Document
public class Organization {

	@Id
	private String id;
	@Indexed(unique = true)
	private String memberId;
	private String icon; // 头像
	private String company; // 机构名称
	private String name; // 机构名称
	private String telephone; // 机构名称
	private String type; // 类型
	// private String number; // 注册号
	// private String legalRepresentative; // 法人
	// private String registeredCapital; // 注册资本
	// private String operatingPeriod; // 经营期限
	// private String scopeOfBusiness; // 经营范围
	// private String registrationAuthority; // 登记机关
	private String address; // 地址
	private String industry; // 应用领域
	// @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
	private GeoJsonPoint location; // GPS 定位信息
	@DBRef
	private List<Multimedia> multimedia; // 资质文件
	private Status status = Status.Disabled; // 状态
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createDate = new Date();
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	public Organization() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public GeoJsonPoint getLocation() {
		return location;
	}

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}

	public List<Multimedia> getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(List<Multimedia> multimedia) {
		this.multimedia = multimedia;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", memberId=" + memberId + ", icon=" + icon + ", company=" + company + ", name=" + name + ", telephone=" + telephone + ", type=" + type + ", address=" + address + ", industry=" + industry + ", location=" + location + ", multimedia=" + multimedia + ", status=" + status + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}

}
