package api.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import api.pojo.Enum.Examine;

@Document
public class Assets {

	@Id
	private String id;

	@Indexed
	private String organizationId; // 上传机构
	@Indexed
	private String memberId; // 权益人

	@Indexed(unique = true, sparse = true)
	private String uuid; // 区块链ID
	@Indexed(unique = true, sparse = true)
	private String qrcode; // 二维码
	@Indexed(unique = true, sparse = true)
	private String transactionId = null; // 区块链交易ID
	@Indexed
	private String nfc; // NFC UID

	private String name; // 名称
	private String description; // 描述
	private String type; // 类型
	private String thumbnail; // 缩图
	private Map<String, String> attribute; // 属性列表
	// @GeoSpatialIndexed
	// @JsonDeserialize(using = GeoJsonDeserializer.class)
	private GeoJsonPoint location; // GPS 定位信息
	@DBRef
	private List<Hypermedia> hypermedia; // 图片，视频
	// private List<History> history; // 历史记录
	// private Certificate certificate; // 鉴定信息
	private Examine status;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date updateDate;

	// private review
	public Assets() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNfc() {
		return nfc;
	}

	public void setNfc(String nfc) {
		this.nfc = nfc;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Map<String, String> getAttribute() {
		return attribute;
	}

	public void setAttribute(Map<String, String> attribute) {
		this.attribute = attribute;
	}

	public GeoJsonPoint getLocation() {
		return location;
	}

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}

	public List<Hypermedia> getHypermedia() {
		return hypermedia;
	}

	public void setHypermedia(List<Hypermedia> hypermedia) {
		this.hypermedia = hypermedia;
	}

	public Examine getStatus() {
		return status;
	}

	public void setStatus(Examine status) {
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
		return "Assets [id=" + id + ", organizationId=" + organizationId + ", memberId=" + memberId + ", uuid=" + uuid + ", transactionId=" + transactionId + ", nfc=" + nfc + ", qrcode=" + qrcode + ", name=" + name + ", description=" + description + ", type=" + type + ", thumbnail=" + thumbnail + ", attribute=" + attribute + ", location=" + location + ", hypermedia=" + hypermedia + ", status=" + status + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
}
