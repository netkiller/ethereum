package api.pojo;

public class Enum {

	public enum Authority {
		User, Experts, Organization, Employees, Administrator
	}

	public enum Status {
		Enabled, Disabled, Yes, No, Y, N
	}

	public enum Examine {
		New, Pending, Rejected, Approved
	}

	public enum Task {
		New, Processing, Canceled, Completed
	}

	public enum Gender {
		Male, Female
	}

	public enum Sex {
		True, False, T, F, Male, Female, Boy, Girl
	}

	public enum Review {
		New, Rejected, Approved
	}

	public enum Message {
		Unread, Read, Reply
	}

	public enum GeoJSON {
		Point
	}

	public Enum() {
		// TODO Auto-generated constructor stub
	}
}
