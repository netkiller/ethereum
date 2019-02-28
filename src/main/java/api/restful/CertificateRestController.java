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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Assets;
import api.domain.Certificate;
import api.domain.Message;
import api.pojo.Enum.Status;
import api.pojo.RestfulResponse;
import api.repository.AssetsRepository;
import api.repository.CertificateRepository;
import api.service.MessageService;

@RestController
@RequestMapping("/certificate")
public class CertificateRestController {

	private static final Logger logger = LoggerFactory.getLogger(CertificateRestController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private MessageService messageService;

	@Autowired
	private AssetsRepository assetsRepository;

	public CertificateRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Certificate json() {

		Certificate certificate = new Certificate();
		certificate.setAssetsId("5bbdb8ee7099aa05e4ddb34d");
		certificate.setMemberId("5bbc5d299ea70d08a0b16e04");
		certificate.setName("张三");
		certificate.setRemark("青花瓷，鉴定物品复合年代特征");
		certificate.setStatus(Status.No);
		certificate.setCreateDate(new Date());
		return certificate;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Certificate certificate) {
		try {
			if (certificate.getAssetsId() == null || certificate.getMemberId() == null) {
				return new RestfulResponse(false, 0, messageSource.getMessage("certificate.create.data.false", null, LocaleContextHolder.getLocale()), null);
			}
			certificate.setCreateDate(new Date());
			certificate.setUpdateDate(null);
			certificate.setStatus(Status.No);
			certificateRepository.save(certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("certificate.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, certificate);
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Certificate certificate) {
		try {
			Certificate tmp = certificateRepository.findOneById(certificate.getId());

			if (tmp.getStatus() == Status.Yes) {
				return new RestfulResponse(false, 0, messageSource.getMessage("certificate.update.yes.false", null, LocaleContextHolder.getLocale()), null);
			}

			if (certificate.getName() != null) {
				tmp.setName(certificate.getName());
			}

			if (certificate.getRemark() != null) {
				tmp.setRemark(certificate.getRemark());
			}
			if (certificate.getStatus() != null) {

				Assets assets = assetsRepository.findOneById(tmp.getAssetsId());
				// Assets assets = assetsRepository.findOneById(certificate.getAssetsId());

				// if (assets.getStatus() == Examine.Approved) {
				// return new RestfulResponse(false, 0, messageSource.getMessage("certificate.update.approved.false", null, LocaleContextHolder.getLocale()), null);
				// }

				tmp.setStatus(certificate.getStatus());
				if (certificate.getStatus() == Status.Yes) {
					messageService.send(new Message(tmp.getMemberId(), "资产鉴定", String.format("%s 已鉴定完毕, 鉴定师 %s。", tmp.getName(), assets.getName())));
					messageService.send(new Message(assets.getMemberId(), "资产鉴定", String.format("%s 已鉴定完毕, 鉴定师 %s。", tmp.getName(), assets.getName())));
				}
			}
			certificate.setUpdateDate(new Date());
			certificateRepository.save(certificate);
		} catch (

		Exception e) {
			String message = messageSource.getMessage("certificate.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("certificate.update.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, certificate);
	}

	@PostMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		try {
			certificateRepository.deleteById(id);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.remove.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("certificate.remove.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, null);
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Certificate certificate = certificateRepository.findOneById(id);
			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/{assetsId}/{memberId}")
	public RestfulResponse get(@PathVariable String assetsId, @PathVariable String memberId) {
		try {
			Certificate certificate = certificateRepository.findOneByAssetsIdAndMemberId(assetsId, memberId);
			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/member/{memberId}")
	public RestfulResponse member(@PathVariable String memberId) {
		try {
			List<Certificate> certificate = certificateRepository.findByMemberId(memberId);
			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/member/{memberId}/{status}/{size}/{page}")
	public RestfulResponse member(@PathVariable String memberId, @PathVariable String status, @PathVariable int size, @PathVariable int page) {
		try {
			List<Certificate> certificates = certificateRepository.findByMemberIdAndStatus(memberId, status, PageRequest.of(page - 1, size));

			List<String> assetsIds = new ArrayList<String>();
			for (Certificate certificate : certificates) {
				assetsIds.add(certificate.getAssetsId());
			}
			logger.info(assetsIds.toString());
			List<Assets> assets = assetsRepository.findAllByIdIn(assetsIds);

			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/assets/{assetsId}")
	public RestfulResponse assets(@PathVariable String assetsId) {
		try {
			List<Certificate> certificate = certificateRepository.findByAssetsId(assetsId);
			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/assets/{assetsId}/{status}")
	public RestfulResponse assets(@PathVariable String assetsId, @PathVariable String status) {
		try {
			List<Certificate> certificate = certificateRepository.findByAssetsIdAndStatus(assetsId, status);
			String message = messageSource.getMessage("certificate.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, certificate);
		} catch (Exception e) {
			String message = messageSource.getMessage("certificate.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}
}
