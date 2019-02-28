package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import api.domain.Employees;
import api.domain.Message;
import api.domain.Multimedia;
import api.pojo.RestfulResponse;
import api.pojo.Enum.Status;
import api.pojo.Enum.Gender;
import api.repository.EmployeesRepository;
import api.repository.MultimediaRepository;
import api.service.MessageService;

@RestController
@RequestMapping("/employees")
public class EmployeesRestController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeesRestController.class);

	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MultimediaRepository multimediaRepository;

	public EmployeesRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Employees json() {
		Employees employees = new Employees();
		employees.setMemberId("00000");
		employees.setIcon("http://wwww.xxx.com/xxx.jpg");
		employees.setName("张三");
		employees.setIdCard("身份证");
		employees.setIdNumber("235364375687234534");
		employees.setAge("18");
		employees.setCreateDate(new Date());
		employees.setOrganizationId("00999");
		employees.setGender(Gender.Male);
		employees.setNickname("送森更温柔");
		employees.setBirthday(new Date());
		employees.setAddress("深圳南山区");

		employees.setLocation(new GeoJsonPoint(Double.valueOf(112.23465), Double.valueOf(12.245676)));

		List<Multimedia> multimedia = new ArrayList<Multimedia>();

		multimedia.add(new Multimedia("aaa", "/sss/sss.jpg", ".jpg"));
		multimedia.add(new Multimedia("bbb", "/bb/bb.jpg", ".jpg"));

		employees.setMultimedia(multimedia);

		employees.setStatus(Status.Disabled);
		return employees;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Employees employees) {
		logger.info(employees.toString());
		try {
			employees.setCreateDate(new Date());
			employees.setUpdateDate(null);
			employees.setStatus(Status.Disabled);

			if (employees.getMultimedia() != null) {
				for (Multimedia multimedia : employees.getMultimedia()) {
					multimediaRepository.save(multimedia);
				}
			}
			logger.info(employees.toString());
			employeesRepository.save(employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("employees.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, employees);
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Employees employees = employeesRepository.findOneById(id);
			String message = messageSource.getMessage("employees.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/member/{memberId}")
	public RestfulResponse getByMemberId(@PathVariable String memberId) {
		try {
			List<Employees> employees = employeesRepository.findByMemberId(memberId);
			String message = messageSource.getMessage("employees.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list")
	@Cacheable("employees:list")
	public RestfulResponse list() {
		try {
			List<Employees> employees = employeesRepository.findAll();
			String message = messageSource.getMessage("employees.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/status/{status}")
	public RestfulResponse listByStatus(@PathVariable String status) {
		try {
			List<Employees> employees = employeesRepository.findAllByStatus(status);
			String message = messageSource.getMessage("employees.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/organization/{organizationId}")
	public RestfulResponse listByOrganizationId(@PathVariable String organizationId) {
		try {
			List<Employees> employees = employeesRepository.findAllByOrganizationIdAndStatus(organizationId, Status.Enabled);
			String message = messageSource.getMessage("employees.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, employees);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Employees employees) {
		logger.info(employees.toString());
		try {
			Employees tmp = employeesRepository.findOneById(employees.getId());

			if (tmp == null) {
				return new RestfulResponse(false, 0, messageSource.getMessage("employees.not.exist", null, LocaleContextHolder.getLocale()), null);
			}

			if (employees.getIcon() != null) {
				tmp.setIcon(employees.getIcon());
			}
			if (employees.getName() != null) {
				tmp.setName(employees.getName());
			}
			if (employees.getNickname() != null) {
				tmp.setNickname(employees.getNickname());
			}
			if (employees.getGender() != null) {
				tmp.setGender(employees.getGender());
			}
			if (employees.getAge() != null) {
				tmp.setAge(employees.getAge());
			}
			if (employees.getAddress() != null) {
				tmp.setAddress(employees.getAddress());
			}
			if (employees.getBirthday() != null) {
				tmp.setBirthday(employees.getBirthday());
			}
			if (employees.getIdCard() != null) {
				tmp.setIdCard(employees.getIdCard());
			}
			if (employees.getIdNumber() != null) {
				tmp.setIdNumber(employees.getIdNumber());
			}

			if (employees.getLocation() != null) {
				tmp.setLocation(employees.getLocation());
			}

			if (employees.getMultimedia() != null) {

				for (Multimedia multimedia : employees.getMultimedia()) {
					multimediaRepository.save(multimedia);
				}
				tmp.setMultimedia(employees.getMultimedia());
			}

			if (employees.getStatus() != null) {
				tmp.setStatus(employees.getStatus());

				if (employees.getStatus() == Status.Enabled) {
					messageService.send(new Message(employees.getMemberId(), "分支机构审核结果", "恭喜您！分行机构已经审批通过。"));
				}
				if (employees.getStatus() == Status.Disabled) {
					messageService.send(new Message(employees.getMemberId(), "分支机构审核结果", "很遗憾！分行机构没有审批通过，请完善资料再提交。"));
				}

			} else {
				tmp.setStatus(Status.Disabled);
			}

			tmp.setOrganizationId(employees.getOrganizationId());

			tmp.setUpdateDate(new Date());
			employeesRepository.save(tmp);

			String message = messageSource.getMessage("employees.update.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, tmp);
		} catch (Exception e) {
			String message = messageSource.getMessage("employees.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

}
