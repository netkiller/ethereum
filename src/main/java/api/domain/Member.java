package api.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import api.pojo.Enum.Authority;

@Document
public class Member {

	@Id
	private String id;
	@Indexed(unique = true)
	private String username; // 用户名，手机登录后可以设置一次，不允许再修改，之后可以使用用户名登录
	@Indexed(unique = true)
	private String mobile; // 手机号码
	private String password; // 密码
	@Indexed
	private String wechat; // 微信ID
	private Authority role; // 机构、授权人 两个权限
	@Transient
	private String captcha;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date updateDate;

	public Member() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Authority getRole() {
		return role;
	}

	public void setRole(Authority role) {
		this.role = role;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
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
		return "Member [id=" + id + ", username=" + username + ", mobile=" + mobile + ", password=" + password + ", wechat=" + wechat + ", role=" + role + ", captcha=" + captcha + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}

}
