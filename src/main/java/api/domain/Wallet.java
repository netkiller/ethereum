package api.domain;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Wallet {
	@Id
	private String id;
	@Indexed(unique = true)
	private String memberId;
	private String address;
	private String privateKey;
	private String mnemonic;
	
	public Wallet() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	@Override
	public String toString() {
		return "Wallet [id=" + id + ", memberId=" + memberId + ", address=" + address + ", privateKey=" + privateKey + ", mnemonic=" + mnemonic + "]";
	}

}
