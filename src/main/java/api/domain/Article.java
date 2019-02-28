package api.domain;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Article {

	@Id
	private String id;
	private String title; // 名称
	private String description; // 描述
	private String tag; // 类型

	List<Author> author;

	public static class Author {
		private String id;
		private String firstname;
		private String lastname;

		public Author(String firstname, String lastname) {
			this.firstname = firstname;
			this.lastname = lastname;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		@Override
		public String toString() {
			return "Author [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + "]";
		}
	}

	@DBRef
	private List<Hypermedia> hypermedia; // 图片，视频

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Hypermedia> getHypermedia() {
		return hypermedia;
	}

	public void setHypermedia(List<Hypermedia> hypermedia) {
		this.hypermedia = hypermedia;
	}

	public List<Author> getAuthor() {
		return author;
	}

	public void setAuthor(List<Author> author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", description=" + description + ", tag=" + tag + ", author=" + author + ", hypermedia=" + hypermedia + "]";
	}

}
