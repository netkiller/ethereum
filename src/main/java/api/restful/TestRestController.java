package api.restful;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Attribute;
import api.domain.Person;
import api.pojo.RestfulResponse;

import api.repository.AttributeRepository;
import api.repository.PersonRepository;

@RestController
@RequestMapping("/test")
// @CacheConfig(cacheNames = "test")
public class TestRestController {
	private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

	// @Autowired
	// TransactionHistoryRepository transactionHistoryRepository;
	//
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AttributeRepository attributeRepository;

	public TestRestController() {

	}

	@GetMapping("/hello")
	public RestfulResponse hello() {
		logger.info("hello");
		return new RestfulResponse(true, 0, "调用成功", "Helloworld!!!");
	}

	@GetMapping("/mongo")
	public Attribute testMongo() {

		// LocalDateTime localDateTime = LocalDateTime.of(2016, 1, 1, 13, 55);
		// ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
		// Date date = Date.from(zonedDateTime.toInstant());

		Date current = new Date();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(current.toInstant(), ZoneId.systemDefault());
		Date out = Date.from(localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant());
		logger.info(ZoneId.systemDefault().toString());
		logger.info(current.toString());
		logger.info(out.toString());
		// ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));

		Attribute attribute = new Attribute();
		attribute.setUpdateDate(out);
		// attribute.setName("Netkiller");
		attributeRepository.save(attribute);

		return attribute;
	}

	@GetMapping("/redis")
	public Person redis() {

		Person children = new Person();
		children.setFirstname("Lisa");
		children.setLastname("Chen");
		children.setGender(Person.Gender.FEMALE);

		Person person = new Person();
		person.setFirstname("Neo");
		person.setLastname("Chen");
		person.setGender(Person.Gender.MALE);

		// List<Person> childrens = new ArrayList<Person>();

		person.setChildren(Arrays.asList(children));

//		Point point = new Point(Double.valueOf("28.352734"), Double.valueOf("32.807382"));
//		Address address = new Address("Shenzhen", "China", point);
//		person.setAddress(address);
		personRepository.save(person);
		return person;
	}

	@GetMapping("/cache")
	@Cacheable("test0")
	public String redis2() {

		return "OK";
	}

	@GetMapping("/cache/expire")
	@Cacheable("test2")
	public String expire() {
		return "Test";
	}

	@GetMapping("/cache/auto")
	@Cacheable()
	public Attribute auto() {
		Attribute attribute = new Attribute();
		// attribute.setName("sdfsdf");
		return attribute;
	}

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/lang")
	public String language() {
		String locale = LocaleContextHolder.getLocale().toString();
		System.out.println(locale);
		System.out.println(LocaleContextHolder.getLocale());
		String message = messageSource.getMessage("member.name", null, LocaleContextHolder.getLocale());
		return message;
	}

	@GetMapping("/lang/args")
	public String welcome() {
		String[] args = { "China" };
		String message = messageSource.getMessage("welcome", args, LocaleContextHolder.getLocale());
		return message;
	}

	@GetMapping("/lang/zh")
	public String lang(@RequestHeader String lang) throws IOException {
		System.out.println(lang);
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(lang + ".properties"));
		String tmp = properties.getProperty("hello");
		return tmp;
	}

}
