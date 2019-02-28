package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Nfc;
import api.pojo.RestfulResponse;
import api.repository.NfcRepository;

@RestController
@RequestMapping("/nfc")
public class NfcRestController {

	@Autowired
	private NfcRepository nfcRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MongoTemplate mongoTemplate;

	public NfcRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Nfc json() {

		Nfc nfc = new Nfc();
		nfc.setUid("A98EF");
		nfc.setUuid("98b2b71a-170d-446c-b1f6-d7b26019bff3");
		nfc.setOrganizationId("32c54f64e42a");
		nfc.setCreateDate(new Date());
		System.out.println(nfc);

		return nfc;
	}

	@PostMapping("/create")
	public RestfulResponse create(Nfc nfc) {
		try {
			nfc.setCreateDate(new Date());
			nfc.setUpdateDate(null);
			nfcRepository.save(nfc);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("message.create.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}
		return new RestfulResponse(true, 0, messageSource.getMessage("message.create.true", null, LocaleContextHolder.getLocale()), nfc);
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Nfc nfc = nfcRepository.findOneById(id);
			return new RestfulResponse(true, 0, messageSource.getMessage("message.create.true", null, LocaleContextHolder.getLocale()), nfc);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("message.create.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}
	}

	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		try {
			nfcRepository.deleteById(id);
			return new RestfulResponse(true, 0, messageSource.getMessage("message.create.true", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("message.create.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}
	}

	@GetMapping("/update")
	public void update() {

	}

}
