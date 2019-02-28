package api.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import api.pojo.Enum.Task;

// 机构邀请专家鉴定资产

@Document
public class InviteCertificate {
	@Id
	private String id;
	private String assetsId; // 资产 ID
	private String memberId; // 专家 ID
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createDate; // 鉴定时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date updateDate; // 完成时间
	private Task status; // 状态 Processing 正在进行的, Completed 完成的

	public InviteCertificate() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(String assetsId) {
		this.assetsId = assetsId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public Task getStatus() {
		return status;
	}

	public void setStatus(Task status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InviteCertificate [id=" + id + ", assetsId=" + assetsId + ", memberId=" + memberId + ", createDate=" + createDate + ", updateDate=" + updateDate + ", status=" + status + "]";
	}

}
