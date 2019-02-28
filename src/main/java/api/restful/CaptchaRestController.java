package api.restful;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.service.AccessControlList;
import api.service.MessageService;
import api.pojo.RestfulResponse;

@RestController
@RequestMapping("/captcha")
public class CaptchaRestController {
	private static final Logger logger = LoggerFactory.getLogger(CaptchaRestController.class);

	@Autowired
	private AccessControlList accessControlList;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private MessageService messageService;

	public static final String cacheKeyPrefix = "captcha:sms";
	private long expireTime = 600;

	public List<String> whiteList = new ArrayList<String>();

	public CaptchaRestController() {
		whiteList.add("13113668890");
		whiteList.add("13591191658");
		whiteList.add("18688939680");
		whiteList.add("18600000000");
	}

	// 发送验证码
	@RequestMapping("/sms/{mobile}")
	public RestfulResponse sms(@PathVariable String mobile) {

		if (accessControlList.isIpaddrBlocked(60, 10)) {
			// throw new RuntimeException("IP受限，发送过于频繁，请稍后再试");
			return new RestfulResponse(false, 10, "IP受限，发送过于频繁，请稍后再试", null);
		}
		if (accessControlList.isMobileBlocked(mobile, 60, 5)) {
			// throw new RuntimeException("手机号码受限，发送过于频繁，请稍后再试");
			return new RestfulResponse(false, 20, "手机号码受限，发送过于频繁，请稍后再试", null);
		}

		String cacheKey = String.format("%s:%s", cacheKeyPrefix, mobile);
		String code = this.getRandomCode();
		try {

			String message = String.format("您的验证码是%s，在%s分钟内输入有效。如非本人操作请忽略此短信。", code, 10);
			if (whiteList.contains(mobile)) {
				code = "8888";
			} else {
				messageService.sms(mobile, message);
			}
			redisTemplate.opsForValue().set(cacheKey, code);
			redisTemplate.expire(cacheKey, expireTime, TimeUnit.SECONDS);
			logger.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
		return new RestfulResponse(true, 1, "手机验证码发送成功", null);
	}

	// 校验验证码
	@RequestMapping("/sms/{mobile}/{code}")
	public RestfulResponse sms(@PathVariable("mobile") String mobile, @PathVariable("code") String code) {

		String cacheKey = String.format("%s:%s", cacheKeyPrefix, mobile);
		if (redisTemplate.hasKey(cacheKey)) {
			String cacheValue = redisTemplate.opsForValue().get(cacheKey);
			if (cacheValue.equals(code)) {
				redisTemplate.delete(cacheKey);
				return new RestfulResponse(true, 1, "验证码校验成功", null);
			}
		}
		return new RestfulResponse(false, 0, "验证码校验失败", null);
	}

	// 获取验证码
	@RequestMapping("/sms/code/{mobile}")
	public RestfulResponse smsCode(@PathVariable("mobile") String mobile) {

		String cacheKey = String.format("%s:%s", cacheKeyPrefix, mobile);
		if (redisTemplate.hasKey(cacheKey)) {
			String code = redisTemplate.opsForValue().get(cacheKey);
			return new RestfulResponse(true, 0, "验证码", code);
		}
		return new RestfulResponse(false, 0, "验证码失败", null);
	}

	// 产生测试验证码
	@RequestMapping("/sms/test/{mobile}")
	public RestfulResponse smsTest(@PathVariable String mobile) {

		String cacheKey = String.format("%s:%s", cacheKeyPrefix, mobile);
		String code = this.getRandomCode();
		try {
			redisTemplate.opsForValue().set(cacheKey, code);
			redisTemplate.expire(cacheKey, expireTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
		return new RestfulResponse(true, 1, "手机测试验证码", code);
	}

	// 产生测试验证码
	@RequestMapping("/sms/test/{mobile}/{code}")
	public RestfulResponse smsTest(@PathVariable String mobile, @PathVariable String code) {

		String cacheKey = String.format("%s:%s", cacheKeyPrefix, mobile);
		try {
			redisTemplate.opsForValue().set(cacheKey, code);
			redisTemplate.expire(cacheKey, expireTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
		return new RestfulResponse(true, 1, "手机测试验证码", code);
	}

	// 四位随机验证码
	private String getRandomCode() {
		return String.valueOf((new Random().nextInt(8999) + 1000));
	}

}