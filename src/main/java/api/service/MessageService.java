package api.service;

import api.domain.Message;

public interface MessageService {

	boolean send(Message message);

	void sms(String mobile, String message);

}
