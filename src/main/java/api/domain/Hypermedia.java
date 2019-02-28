package api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hypermedia {

	@Id
	private String id;
	private String hash;
	private String name;
	private String size;

	public Hypermedia() {
		// TODO Auto-generated constructor stub
	}

	public Hypermedia(String hash, String name, String size) {
		this.hash = hash;
		this.name = name;
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Hypermedia [id=" + id + ", hash=" + hash + ", name=" + name + ", size=" + size + "]";
	}

}
