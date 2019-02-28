package api.restful;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.ShippingAddress;
import api.pojo.RestfulResponse;
import api.repository.ShippingAddressRepository;

@RestController
@RequestMapping("/shippingaddress")
public class ShippingAddressRestController {

	private static final Logger logger = LoggerFactory.getLogger(ShippingAddressRestController.class);

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MongoTemplate mongoTemplate;

	public ShippingAddressRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public RestfulResponse json() {
		ShippingAddress address = new ShippingAddress();
		address.setMemberId("00000000");
		address.setAddress("深圳市南山区西丽 56 号");
		address.setName("小明");
		address.setPhone("1304456224");
		address.setDefaults(true);
		address.setCreateDate(new Date());
		address.setUpdateDate(null);

		return new RestfulResponse(true, 0, "添加地址成功", address);
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody ShippingAddress shippingAddress) {
		// ShippingAddress address = new ShippingAddress();
		if (shippingAddress.isDefaults() == true) {
			this.resetAllDefault(shippingAddress.getMemberId());
		}
		shippingAddress.setCreateDate(new Date());
		shippingAddress.setUpdateDate(null);
		shippingAddressRepository.save(shippingAddress);
		return new RestfulResponse(true, 0, "添加地址成功", shippingAddress);
	}

	@GetMapping("/list/member/{memberId}")
	public RestfulResponse list(@PathVariable String memberId) {
		List<ShippingAddress> shippingAddress = shippingAddressRepository.findAllByMemberIdOrderByDefaultsDesc(memberId);
		return new RestfulResponse(true, 0, "添加地址成功", shippingAddress);
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody ShippingAddress shippingAddress) {
		logger.info(shippingAddress.toString());
		ShippingAddress address = new ShippingAddress();
		try {
			address = shippingAddressRepository.findOneById(shippingAddress.getId());

			logger.info(shippingAddress.toString());
			logger.info(address.toString());

			if (shippingAddress.getAddress() != null) {
				address.setAddress(shippingAddress.getAddress());
			}
			if (shippingAddress.getName() != null) {
				address.setName(shippingAddress.getName());
			}
			if (shippingAddress.getPhone() != null) {
				address.setPhone(shippingAddress.getPhone());
			}
			if (shippingAddress.isDefaults() == true) {
				this.resetAllDefault(address.getMemberId());
			}
			address.setDefaults(shippingAddress.isDefaults());

			address.setUpdateDate(new Date());
			shippingAddressRepository.save(address);
			return new RestfulResponse(true, 0, messageSource.getMessage("shippingaddress.update.true", null, LocaleContextHolder.getLocale()), address);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("shippingaddress.update.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}

	}

	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		shippingAddressRepository.deleteById(id);
		return new RestfulResponse(true, 0, "地址删除成功", null);
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		ShippingAddress address = shippingAddressRepository.findOneById(id);
		return new RestfulResponse(true, 0, "地址获取成功", address);
	}

	private void resetAllDefault(String memberId) {

		Query query = Query.query(Criteria.where("memberId").is(memberId));
		Update update = new Update().set("default", false);
		mongoTemplate.updateMulti(query, update, ShippingAddress.class);

	}
}
