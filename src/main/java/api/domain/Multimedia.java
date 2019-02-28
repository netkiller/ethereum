package api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Multimedia {
	@Id
	private String id;
	private String filename;
	private String url;
	private String extension;

	public Multimedia() {
		// TODO Auto-generated constructor stub
	}

	public Multimedia(String filename, String url, String extension) {
		this.filename = filename;
		this.url = url;
		this.extension = extension;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "Multimedia [id=" + id + ", filename=" + filename + ", url=" + url + ", extension=" + extension + "]";
	}

}
