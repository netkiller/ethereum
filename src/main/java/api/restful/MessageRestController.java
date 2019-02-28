package api.restful;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Message;
import api.pojo.Enum;
import api.pojo.RestfulResponse;
import api.repository.MessageRepository;

@RestController
@RequestMapping("/message")
public class MessageRestController {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageSource messageSource;

	public MessageRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Message json() {
		Message message = new Message();
		message.setMemberId("00000");
		message.setTitle("审批通知");
		message.setMessage("你的资产已经审批通过");
		message.setStatus(Enum.Message.Unread);
		message.setCreateDate(new Date());
		message.setUpdateDate(null);
		return message;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Message message) {
		try {
			message.setCreateDate(new Date());
			message.setUpdateDate(null);
			messageRepository.save(message);
		} catch (Exception e) {
			String language = messageSource.getMessage("message.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, language, e.getMessage());
		}
		String language = messageSource.getMessage("message.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, language, message);
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Message message) {
		try {
			Message tmp = messageRepository.findOneById(message.getId());
			if (tmp != null) {
				if (message.getStatus() != null) {
					tmp.setStatus(message.getStatus());
				}
				tmp.setUpdateDate(new Date());
				messageRepository.save(tmp);
				String language = messageSource.getMessage("message.update.true", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(true, 0, language, tmp);
			} else {
				String language = messageSource.getMessage("message.update.false", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, language, null);
			}
		} catch (Exception e) {
			String language = messageSource.getMessage("message.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, language, e.getMessage());
		}
	}

	@GetMapping("/list/member/{memberId}/{size}/{page}")
	public RestfulResponse list(@PathVariable String memberId, @PathVariable int size, @PathVariable int page) {
		try {
			List<Message> message = messageRepository.findAllByMemberId(memberId, PageRequest.of(page - 1, size));
			String language = messageSource.getMessage("message.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, language, message);
		} catch (Exception e) {
			String language = messageSource.getMessage("message.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, language, e.getMessage());
		}
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Message message = messageRepository.findOneById(id);
			if (message != null) {
				message.setStatus(Enum.Message.Read);
				message.setUpdateDate(new Date());
				messageRepository.save(message);
			}
			String language = messageSource.getMessage("message.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, language, message);
		} catch (Exception e) {
			String language = messageSource.getMessage("message.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, language, e.getMessage());
		}
	}
	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		try {
			messageRepository.deleteById(id);
			String language = messageSource.getMessage("message.remove.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, language, null);
		} catch (Exception e) {
			String language = messageSource.getMessage("message.remove.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, language, e.getMessage());
		}
	}
}
