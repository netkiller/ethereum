package api.restful;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Attribute;
import api.pojo.RestfulResponse;
import api.repository.AttributeRepository;

@RestController
@RequestMapping("/attribute")
public class AttributeRestController {

	@Autowired
	private AttributeRepository attributeRepository;

	@Autowired
	private MessageSource messageSource;

	public AttributeRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Attribute json() {
		Attribute attribute = new Attribute();
		attribute.setOrganizationId("00000");
		attribute.setItem(Arrays.asList("color", "weight", "name", "years", "size"));
		return attribute;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Attribute attribute) {
		try {
			attributeRepository.save(attribute);
		} catch (Exception e) {
			String message = messageSource.getMessage("attribute.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("attribute.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(false, 0, message, attribute);
	}

	@GetMapping("/item")
	public RestfulResponse item() {

		Map<String, List<String>> items = new LinkedHashMap<String, List<String>>();
		items.put("free", Arrays.asList("name", "weight", "shape", "color", "size", "height", "width", "years", "age"));
		items.put("personnel", Arrays.asList("tester", "supervisor", "witness", "holder", "author", "editor", "review", "auditor", "approver"));
		items.put("trade", Arrays.asList("price", "fee", "tax"));
		items.put("owner", Arrays.asList("museum", "gallery", "studio"));

		return new RestfulResponse(true, 0, "", items);

	}

	@GetMapping("/get/{organizationId}")
	public RestfulResponse get(@PathVariable String organizationId) {
		try {
			Attribute attribute = attributeRepository.findOneByOrganizationId(organizationId);
			String message = messageSource.getMessage("history.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, attribute);
		} catch (Exception e) {
			String message = messageSource.getMessage("history.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}
}
