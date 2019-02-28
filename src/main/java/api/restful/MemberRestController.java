package api.restful;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Employees;
import api.domain.Member;
import api.domain.Organization;
import api.domain.Profile;
import api.domain.Wallet;
import api.pojo.Enum;
import api.pojo.Enum.Authority;
import api.pojo.RestfulResponse;
import api.repository.EmployeesRepository;
import api.repository.MemberRepository;
import api.repository.OrganizationRepository;
import api.repository.ProfileRepository;
import api.repository.WalletRepository;
import api.service.EthereumWallet;
import api.wallet.Ethereum.WalletResponse;

@RestController
@RequestMapping("/member")
public class MemberRestController {

	private static final Logger logger = LoggerFactory.getLogger(MemberRestController.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private EthereumWallet ethereumWallet;

	@Autowired
	private WalletRepository walletRepository;

	public MemberRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public RestfulResponse json() {
		Member member = new Member();
		member.setMobile("12322228888");
		member.setUsername("12322228888");
		member.setPassword("123456");
		member.setWechat("微信ID");
		member.setRole(Enum.Authority.Organization);
		member.setCreateDate(new Date());
		return new RestfulResponse(false, 0, "", member);
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Member member) {

		try {

			String cacheKey = String.format("%s:%s", CaptchaRestController.cacheKeyPrefix, member.getMobile());
			if (redisTemplate.hasKey(cacheKey)) {
				String cacheValue = redisTemplate.opsForValue().get(cacheKey);
				if (cacheValue.equals(member.getCaptcha())) {

					if (memberRepository.existsByMobile(member.getMobile())) {
						return new RestfulResponse(false, 0, messageSource.getMessage("member.not.exist", null, LocaleContextHolder.getLocale()), null);
					}

					redisTemplate.delete(cacheKey);

					member.setCreateDate(new Date());
					member.setUpdateDate(null);
					logger.info(member.toString());

					member.setUsername(member.getMobile());
					String password = this.md5(member.getPassword());
					member.setPassword(password);
					Member save = memberRepository.save(member);

					WalletResponse walletAccount = ethereumWallet.create();

					Wallet wallet = new Wallet();
					wallet.setAddress(walletAccount.getAddress());
					wallet.setMemberId(save.getId());
					wallet.setMnemonic(walletAccount.getMnemonic());
					wallet.setPrivateKey(walletAccount.getPrivateKey());
					walletRepository.save(wallet);

					return new RestfulResponse(true, 0, messageSource.getMessage("member.create.true", null, LocaleContextHolder.getLocale()), member);
				} else {
					return new RestfulResponse(false, 0, messageSource.getMessage("captcha.error", null, LocaleContextHolder.getLocale()), null);
				}
			} else {
				return new RestfulResponse(false, 0, messageSource.getMessage("captcha.expire", null, LocaleContextHolder.getLocale()), null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, e.getMessage());
		}

	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Member member) {
		try {
			Member tmp = memberRepository.findOneById(member.getId());

			if (tmp == null) {
				return new RestfulResponse(false, 0, messageSource.getMessage("member.update.false", null, LocaleContextHolder.getLocale()), null);
			}

			if (member.getUsername() != null) {
				tmp.setUsername(member.getUsername());
			}
			if (member.getWechat() != null) {
				tmp.setWechat(member.getWechat());
			}
			if (member.getPassword() != null) {
				String password = this.md5(member.getPassword());
				tmp.setPassword(password);
			}
			tmp.setUpdateDate(new Date());

			logger.info(tmp.toString());
			memberRepository.save(tmp);

			String message = messageSource.getMessage("member.update.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, tmp);

		} catch (Exception e) {
			String message = messageSource.getMessage("error.update", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, e.getMessage());
		}
	}

	@PostMapping("/login/password")
	public RestfulResponse loginPassword(@RequestBody Member member) {

		logger.info(member.toString());

		try {
			String password = this.md5(member.getPassword());

			Member tmp = memberRepository.findOneByMobile(member.getMobile());

			if (tmp == null) {
				return new RestfulResponse(false, 0, messageSource.getMessage("member.not.exist", null, LocaleContextHolder.getLocale()), null);
			}
			logger.info(tmp.toString());

			if (tmp.getMobile().equals(member.getMobile()) && tmp.getPassword().equals(password)) {

				return this.loginReturn(tmp);

			} else if (tmp.getUsername().equals(member.getUsername()) && tmp.getPassword().equals(password)) {
				return this.loginReturn(tmp);
			} else {
				String message = messageSource.getMessage("member.password.invalid", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, message, null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/login/mobile/{mobile}/{code}")
	public RestfulResponse loginMobile(@PathVariable("mobile") String mobile, @PathVariable("code") String code) {

		try {
			Member tmp = memberRepository.findOneByMobile(mobile);
			logger.info(tmp.toString());
			if (tmp != null && !tmp.getMobile().isEmpty()) {

				String cacheKey = String.format("%s:%s", CaptchaRestController.cacheKeyPrefix, mobile);
				if (redisTemplate.hasKey(cacheKey)) {
					String cacheValue = redisTemplate.opsForValue().get(cacheKey);
					if (cacheValue.equals(code)) {
						redisTemplate.delete(cacheKey);
						return this.loginReturn(tmp);
					} else {
						return new RestfulResponse(false, 0, messageSource.getMessage("captcha.error", null, LocaleContextHolder.getLocale()), null);
					}
				} else {
					return new RestfulResponse(false, 0, messageSource.getMessage("captcha.expire", null, LocaleContextHolder.getLocale()), null);
				}
			} else {
				return new RestfulResponse(false, 0, messageSource.getMessage("member.not.exist", null, LocaleContextHolder.getLocale()), null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/login/token")
	public RestfulResponse loginToken(@RequestBody Member member) {
		logger.info(member.toString());
		try {
			Member tmp = memberRepository.findOneByMobile(member.getMobile());
			logger.info(tmp.toString());
			if (!tmp.getMobile().isEmpty()) {
				return this.loginReturn(tmp);
			} else {
				String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, message, null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/login/wechat")
	public RestfulResponse loginWechat(@RequestBody Member member) {
		logger.info(member.toString());
		try {
			Member tmp = memberRepository.findOneByWechat(member.getWechat());
			logger.info(tmp.toString());
			if (!tmp.getWechat().isEmpty()) {
				return this.loginReturn(tmp);
			} else {
				String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, message, null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/login/mnemonic")
	public RestfulResponse loginMnemonic(@RequestBody Map<String, String> param) {

		try {
			logger.info(param.toString());
			String mnemonic = ethereumWallet.loginByMnemonic(param.get("mnemonic"));
			Wallet wallet = walletRepository.findOneByMnemonic(mnemonic);
			Member member = memberRepository.findOneById(wallet.getMemberId());
			logger.info(member.toString());
			// if (wallet == null) {
			// return new RestfulResponse(false, 0, messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale()), null);
			// }
			return this.loginReturn(member);

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/login/privatekey")
	public RestfulResponse loginPrivateKey(@RequestBody Map<String, String> param) {

		try {
			logger.info(param.toString());
			String privateKey = ethereumWallet.loginByPrivateKey(param.get("privateKey"));
			Wallet wallet = walletRepository.findOneByPrivateKey(privateKey);
			Member member = memberRepository.findOneById(wallet.getMemberId());
			logger.info(member.toString());
			// if (wallet == null) {
			// return new RestfulResponse(false, 0, messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale()), null);
			// }
			return this.loginReturn(member);

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	private String md5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"))).toLowerCase();
	}

	private RestfulResponse loginReturn(Member tmp) {
		String message = messageSource.getMessage("member.login.true", null, LocaleContextHolder.getLocale());
		Map<String, Object> rev = new LinkedHashMap<String, Object>();
		rev.put("Member", tmp);

		Wallet wallet = walletRepository.findOneByMemberId(tmp.getId());
		rev.put("Wallet", wallet);

		if (tmp.getRole() == Authority.Organization || tmp.getRole() == Authority.Administrator) {
			Organization organization = organizationRepository.findOneByMemberId(tmp.getId());
			rev.put(tmp.getRole().toString(), organization);
			return new RestfulResponse(true, 0, message, rev);
		} else if (tmp.getRole() == Authority.Employees) {
			Employees employees = employeesRepository.findOneByMemberId(tmp.getId());
			rev.put("Employees", employees);
			return new RestfulResponse(true, 0, message, rev);
		} else {
			Profile profile = profileRepository.findOneByMemberId(tmp.getId());
			rev.put("Profile", profile);
			return new RestfulResponse(true, 0, message, rev);
		}
		// return new RestfulResponse(true, 0, message, rev);
	}

	@PostMapping("/password/reset")
	public RestfulResponse reset(@RequestBody Member member) {

		try {

			String cacheKey = String.format("%s:%s", CaptchaRestController.cacheKeyPrefix, member.getMobile());
			if (redisTemplate.hasKey(cacheKey)) {
				String cacheValue = redisTemplate.opsForValue().get(cacheKey);
				if (cacheValue.equals(member.getCaptcha())) {

					Member tmp = memberRepository.findOneByMobile(member.getMobile());

					if (tmp == null) {
						return new RestfulResponse(false, 0, messageSource.getMessage("member.not.exist", null, LocaleContextHolder.getLocale()), null);
					}
					String password = this.md5(member.getPassword());
					tmp.setPassword(password);
					redisTemplate.delete(cacheKey);
					memberRepository.save(tmp);

					logger.info(tmp.toString());
					return new RestfulResponse(true, 0, messageSource.getMessage("member.password.reset", null, LocaleContextHolder.getLocale()), null);

				} else {
					return new RestfulResponse(false, 0, messageSource.getMessage("captcha.error", null, LocaleContextHolder.getLocale()), null);
				}
			} else {
				return new RestfulResponse(false, 0, messageSource.getMessage("captcha.expire", null, LocaleContextHolder.getLocale()), null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("member.login.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}
}