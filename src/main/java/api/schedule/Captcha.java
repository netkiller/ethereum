package api.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Captcha {
	private static final Logger logger = LoggerFactory.getLogger(Captcha.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Captcha() {
		// TODO Auto-generated constructor stub
	}

	@Scheduled(fixedRate = 1000 * 60 * 10) // 5秒运行一次调度任务
	public void echoCurrentTime() {
		logger.info("The time is now {}", dateFormat.format(new Date()));
	}
}
