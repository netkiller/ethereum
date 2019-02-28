package api.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Logging {

	public enum Priority {
		info, warning, error, critical, exception, debug
	}

	public enum Facility {
		app, sms, ios, android, api, db
	}

	@Id
	private String id;
	private String tag;
	private Date time;
	private Facility facility;
	private Priority priority;
	private String message;

	public Logging() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Logging [id=" + id + ", tag=" + tag + ", time=" + time + ", facility=" + facility + ", priority=" + priority + ", message=" + message + "]";
	}

}
