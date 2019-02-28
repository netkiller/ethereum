package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Message;
import api.domain.Multimedia;
import api.domain.Organization;
import api.pojo.RestfulResponse;
import api.pojo.Enum.Status;
import api.repository.MultimediaRepository;
import api.repository.OrganizationRepository;
import api.service.MessageService;

@RestController
@RequestMapping("/organization")
public class OrganizationRestController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MultimediaRepository multimediaRepository;

	public OrganizationRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Organization json() {
		Organization organization = new Organization();
		organization.setIcon("test.jpg");
		organization.setMemberId("5b8f3d7e917d6a265d2b28cb");
		organization.setName("张三");
		organization.setCompany("XXX 艺术品管理公司");
		organization.setTelephone("0755-11112222");
		organization.setAddress("深圳市南山区西丽");
		organization.setCreateDate(new Date());
		organization.setUpdateDate(new Date());
		// organization.setNumber("12352345");
		// organization.setLegalRepresentative("张松松");
		// organization.setOperatingPeriod("5年");
		// organization.setRegisteredCapital("33000万");
		// organization.setRegistrationAuthority("深圳工商局");
		// organization.setScopeOfBusiness("艺术品拍卖，电商，策展");

		organization.setType("Test");
		organization.setIndustry("艺术品行业");
		organization.setStatus(Status.Disabled);

		List<Multimedia> multimedia = new ArrayList<Multimedia>();

		multimedia.add(new Multimedia("正面图片", "/sss/sss.jpg", ".jpg"));
		multimedia.add(new Multimedia("侧面图片", "/bb/bb.jpg", ".jpg"));

		organization.setMultimedia(multimedia);
		organization.setLocation(new GeoJsonPoint(Double.valueOf(112.23465), Double.valueOf(12.245676)));

		return organization;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Organization organization) {
		boolean thumbnail = true;
		try {
			organization.setCreateDate(new Date());
			organization.setUpdateDate(null);
			organization.setStatus(Status.Disabled);

			if (organization.getMultimedia() != null) {
				for (Multimedia multimedia : organization.getMultimedia()) {

					if (thumbnail) {
						organization.setIcon(multimedia.getUrl());
						thumbnail = false;
					}

					multimediaRepository.save(multimedia);
				}
			}
			organizationRepository.save(organization);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("organization.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(false, 0, message, organization);
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Organization organization) {
		try {

			logger.info(organization.toString());

			Organization tmp = organizationRepository.findOneByMemberId(organization.getMemberId());

			if (organization.getCompany() != null) {
				tmp.setCompany(organization.getCompany());
			}

			if (organization.getName() != null) {
				tmp.setName(organization.getName());
			}
			if (organization.getTelephone() != null) {
				tmp.setTelephone(organization.getTelephone());
			}
			// if (organization.getNumber() != null) {
			// tmp.setNumber(organization.getNumber());
			// }
			if (organization.getAddress() != null) {
				tmp.setAddress(organization.getAddress());
			}
			// if (organization.getLegalRepresentative() != null) {
			// tmp.setLegalRepresentative(organization.getLegalRepresentative());
			// }
			// if (organization.getOperatingPeriod() != null) {
			// tmp.setOperatingPeriod(organization.getOperatingPeriod());
			// }
			// if (organization.getRegisteredCapital() != null) {
			// tmp.setRegisteredCapital(organization.getRegisteredCapital());
			// }
			// if (organization.getRegistrationAuthority() != null) {
			// tmp.setRegistrationAuthority(organization.getRegistrationAuthority());
			// }
			// if (organization.getScopeOfBusiness() != null) {
			// tmp.setScopeOfBusiness(organization.getScopeOfBusiness());
			// }

			if (organization.getType() != null) {
				tmp.setType(organization.getType());
			}
			if (organization.getIndustry() != null) {
				tmp.setIndustry(organization.getIndustry());
			}

			if (organization.getMultimedia() != null) {

				for (Multimedia multimedia : organization.getMultimedia()) {
					multimediaRepository.save(multimedia);
				}

				tmp.setMultimedia(organization.getMultimedia());
			}
			if (organization.getLocation() != null) {
				tmp.setLocation(organization.getLocation());
			}

			if (organization.getStatus() != null) {
				tmp.setStatus(organization.getStatus());
				if (organization.getStatus() == Status.Enabled) {
					messageService.send(new Message(organization.getMemberId(), "分支机构审核结果", "恭喜您！分行机构已经审批通过。"));
				}
				if (organization.getStatus() == Status.Disabled) {
					messageService.send(new Message(organization.getMemberId(), "分支机构审核结果", "很遗憾！分行机构没有审批通过，请完善资料再提交。"));
				}
			}

			tmp.setUpdateDate(new Date());

			logger.info(tmp.toString());

			organizationRepository.save(tmp);
			String message = messageSource.getMessage("organization.update.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, tmp);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}

	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Organization organization = organizationRepository.findOneById(id);
			String message = messageSource.getMessage("organization.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, organization);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/member/{memberId}")
	public RestfulResponse getByMemberId(@PathVariable String memberId) {
		try {
			Organization organization = organizationRepository.findOneByMemberId(memberId);
			String message = messageSource.getMessage("organization.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, organization);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list")
	public RestfulResponse list() {
		try {
			List<Organization> organization = organizationRepository.findAll();
			String message = messageSource.getMessage("organization.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, organization);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/{status}/{size}/{page}")
	public RestfulResponse list(@PathVariable String status, @PathVariable int size, @PathVariable int page) {
		try {
			List<Organization> organization = organizationRepository.findAllByStatus(status, PageRequest.of(page - 1, size));
			String message = messageSource.getMessage("organization.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, organization);
		} catch (Exception e) {
			String message = messageSource.getMessage("organization.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

}
