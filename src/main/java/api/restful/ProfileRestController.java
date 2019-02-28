package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Profile;
import api.domain.Message;
import api.domain.Multimedia;
import api.pojo.RestfulResponse;
import api.pojo.Enum.Status;
import api.pojo.Enum.Gender;
import api.repository.ProfileRepository;
import api.repository.MultimediaRepository;
import api.service.MessageService;

@RestController
@RequestMapping("/profile")
public class ProfileRestController {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MultimediaRepository multimediaRepository;

	public ProfileRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Profile json() {
		Profile profile = new Profile();
		profile.setMemberId("00000");
		profile.setIcon("http://wwww.xxx.com/xxx.jpg");
		profile.setName("张三");
		profile.setIdCard("身份证");
		profile.setIdNumber("235364375687234534");
		profile.setAge("18");
		profile.setCreateDate(new Date());
		profile.setGender(Gender.Male);
		profile.setNickname("送森更温柔");
		profile.setBirthday(new Date());

		profile.setLocation(new GeoJsonPoint(Double.valueOf(112.23465), Double.valueOf(12.245676)));

		List<Multimedia> multimedia = new ArrayList<Multimedia>();

		multimedia.add(new Multimedia("aaa", "/sss/sss.jpg", ".jpg"));
		multimedia.add(new Multimedia("bbb", "/bb/bb.jpg", ".jpg"));

		profile.setMultimedia(multimedia);

		profile.setStatus(Status.Enabled);
		return profile;
	}

	@PostMapping("/create")
	public RestfulResponse create(Profile profile) {
		try {
			profile.setCreateDate(new Date());
			profile.setUpdateDate(null);
			profile.setStatus(Status.Enabled);

			if (profile.getMultimedia() != null) {
				for (Multimedia multimedia : profile.getMultimedia()) {
					multimediaRepository.save(multimedia);
				}
			}
			profileRepository.save(profile);
		} catch (

		Exception e) {
			String message = messageSource.getMessage("profile.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("profile.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, profile);
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Profile profile = profileRepository.findOneById(id);
			String message = messageSource.getMessage("profile.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, profile);
		} catch (Exception e) {
			String message = messageSource.getMessage("profile.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/member/{memberId}")
	public RestfulResponse getByMemberId(@PathVariable String memberId) {
		try {
			Profile profile = profileRepository.findByMemberId(memberId);
			String message = messageSource.getMessage("profile.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, profile);
		} catch (Exception e) {
			String message = messageSource.getMessage("profile.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list")
	@Cacheable("profile:list")
	public RestfulResponse list() {
		try {
			List<Profile> profile = profileRepository.findAll();
			String message = messageSource.getMessage("profile.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, profile);
		} catch (Exception e) {
			String message = messageSource.getMessage("profile.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/status/{status}")
	public RestfulResponse listByStatus(@PathVariable String status) {
		try {
			List<Profile> profile = profileRepository.findAllByStatus(status);
			String message = messageSource.getMessage("profile.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, profile);
		} catch (Exception e) {
			String message = messageSource.getMessage("profile.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Profile profile) {
		try {
			Profile tmp = profileRepository.findOneById(profile.getId());

			if (profile.getIcon() != null) {
				tmp.setIcon(profile.getIcon());
			}
			if (profile.getName() != null) {
				tmp.setName(profile.getName());
			}
			if (profile.getNickname() != null) {
				tmp.setNickname(profile.getNickname());
			}
			if (profile.getGender() != null) {
				tmp.setGender(profile.getGender());
			}
			if (profile.getAge() != null) {
				tmp.setAge(profile.getAge());
			}
			if (profile.getAddress() != null) {
				tmp.setAddress(profile.getAddress());
			}
			if (profile.getBirthday() != null) {
				tmp.setBirthday(profile.getBirthday());
			}
			if (profile.getIdCard() != null) {
				tmp.setIdCard(profile.getIdCard());
			}
			if (profile.getIdNumber() != null) {
				tmp.setIdNumber(profile.getIdNumber());
			}

			if (profile.getLocation() != null) {
				tmp.setLocation(profile.getLocation());
			}

			if (profile.getMultimedia() != null) {

				for (Multimedia multimedia : profile.getMultimedia()) {
					multimediaRepository.save(multimedia);
				}
				tmp.setMultimedia(profile.getMultimedia());
			}

			if (profile.getStatus() != null) {
				tmp.setStatus(profile.getStatus());

				if (profile.getStatus() == Status.Enabled) {
					messageService.send(new Message(profile.getMemberId(), "账号状态", "恭喜您！账号已经解冻。"));
				}
				if (profile.getStatus() == Status.Disabled) {
					messageService.send(new Message(profile.getMemberId(), "账号状态", "很遗憾！您的账号已经被冻结，请联系客服。"));
				}

			} else {
				tmp.setStatus(Status.Disabled);
			}

			tmp.setUpdateDate(new Date());
			profileRepository.save(tmp);

			String message = messageSource.getMessage("profile.update.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, tmp);
		} catch (Exception e) {
			String message = messageSource.getMessage("profile.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

}
