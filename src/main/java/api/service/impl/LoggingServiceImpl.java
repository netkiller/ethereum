package api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.domain.Logging;
import api.domain.Logging.Facility;
import api.domain.Logging.Priority;
import api.repository.LoggingRepository;
import api.service.LoggingService;

@Service
public class LoggingServiceImpl implements LoggingService {

	@Autowired
	private LoggingRepository loggingRepository;

	public LoggingServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	private void save(Priority priority, String tag, Facility facility, String message) {

		Logging logging = new Logging();
		logging.setTime(new Date());
		logging.setTag(tag);
		logging.setPriority(priority);
		logging.setFacility(facility);
		logging.setMessage(message);
		loggingRepository.save(logging);
	}

}
