package api.service;

public interface AccessControlList {

	boolean isIpaddrBlocked(long interval, int attempts);
	boolean isMobileBlocked(String mobile, long interval, int attempts);

}
