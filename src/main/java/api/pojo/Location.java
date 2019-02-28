package api.pojo;

public class Location {

	private String longitude; // 经度
	private String latitude; // 纬度

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(String longitude, String latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
