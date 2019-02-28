package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import api.domain.Assets;
import api.domain.Certificate;
import api.domain.Comment;
import api.domain.History;
import api.domain.Hypermedia;
import api.domain.Message;
import api.domain.Organization;
import api.pojo.Enum.Examine;
import api.pojo.RestfulResponse;
import api.repository.AssetsRepository;
import api.repository.CertificateRepository;
import api.repository.CommentRepository;
import api.repository.HistoryRepository;
import api.repository.HypermediaRepository;
import api.repository.OrganizationRepository;
import api.service.BlockchainService;
import api.service.MessageService;

@RestController
@RequestMapping("/assets")
public class AssetsRestController {

	private static final Logger logger = LoggerFactory.getLogger(AssetsRestController.class);

	@Autowired
	private AssetsRepository assetsRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BlockchainService blockchainService;

	@Autowired
	private HypermediaRepository hypermediaRepository;

	@Autowired
	private MessageService messageService;

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private CommentRepository commentRepository;

	public AssetsRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public RestfulResponse json() {
		Assets assets = new Assets();

		assets.setOrganizationId("12");
		assets.setMemberId("1234");
		assets.setTransactionId(null);
		assets.setUuid("20F786DB-5CBF-4525-9526-1E88C91D0DD0".toLowerCase());
		assets.setQrcode("20F786DB-5CBF-4525-9526-1E88C91D0DD0".toLowerCase());
		assets.setName("千里江山图");
		assets.setDescription("《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。该作品以长卷形式，立足传统，画面细致入微，烟波浩渺的江河、层峦起伏的群山构成了一幅美妙的江南山水图，渔村野市、水榭亭台、茅庵草舍、水磨长桥等静景穿插捕鱼、驶船、游玩、赶集等动景，动静结合恰到好处。");
		assets.setType("书画");
		assets.setThumbnail("http://xxxxx/xxxx.jpg");

		Map<String, String> attribute = new LinkedHashMap<String, String>();
		attribute.put("检验结论", "天然翡翠挂件（A）");
		attribute.put("检验结论", "天然翡翠挂件（A）");
		attribute.put("总质量", "5.64g");
		attribute.put("形状", "圆环形");
		attribute.put("贵金属标注", "-----");
		attribute.put("放大检查", "纤维交织结构");

		assets.setAttribute(attribute);

		List<Hypermedia> hypermedia = new ArrayList<Hypermedia>();

		hypermedia.add(new Hypermedia("QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn", "aaa.jpg", ""));
		hypermedia.add(new Hypermedia("QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn", "bbb.jpg", ""));

		assets.setHypermedia(hypermedia);

		double longitude = 132.32356;
		double latitude = 12.323456;
		assets.setLocation(new GeoJsonPoint(Double.valueOf(longitude), Double.valueOf(latitude)));

		// List<History> history = new ArrayList<History>();
		// history.add(new History(new Date(), Status.OK, "录入数据"));
		// history.add(new History(new Date(), Status.True, "交易成功"));
		// assets.setHistory(history);

		assets.setStatus(Examine.New);
		// assets.setCertificate(certificate);
		assets.setCreateDate(new Date());

		assets.setCreateDate(new Date());
		assets.setUpdateDate(null);

		return new RestfulResponse(true, 0, "创建成功", assets);
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Assets assets) {
		boolean thumbnail = true;
		try {
			// assets.setTransactionId("");
			// assets.setUuid(assets.getUuid().toLowerCase());
			assets.setCreateDate(new Date());
			assets.setUpdateDate(null);
			assets.setStatus(Examine.New);

			if (assets.getHypermedia() != null) {
				for (Hypermedia hypermedia : assets.getHypermedia()) {

					if (thumbnail) {
						assets.setThumbnail(hypermedia.getHash());
						thumbnail = false;
					}

					hypermediaRepository.save(hypermedia);
				}
			}

			logger.info(assets.toString());

			assetsRepository.save(assets);
		} catch (Exception e) {
			logger.error(assets.toString());
			String message = messageSource.getMessage("assets.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		logger.error("create {}", assets.toString());
		String message = messageSource.getMessage("assets.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, assets);
	}

	@GetMapping("/list/status/{status}/{size}/{page}")
	public RestfulResponse list(@PathVariable Examine status, @PathVariable int size, @PathVariable int page) {
		try {
			List<Assets> assets = assetsRepository.findAllByStatus(status, PageRequest.of(page - 1, size));
			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/organization/{organizationId}/{size}/{page}")
	public RestfulResponse list(@PathVariable String organizationId, @PathVariable int size, @PathVariable int page) {
		try {
			List<Assets> assets = assetsRepository.findAllByOrganizationId(organizationId, PageRequest.of(page - 1, size));

			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/organization/{organizationId}/{status}/{size}/{page}")
	public RestfulResponse list(@PathVariable String organizationId, @PathVariable String status, @PathVariable int size, @PathVariable int page) {
		try {
			List<Assets> assets = assetsRepository.findAllByOrganizationIdAndStatus(organizationId, status, PageRequest.of(page - 1, size));

			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/{id}")
	public RestfulResponse get(@PathVariable String id) {
		try {
			Assets assets = assetsRepository.findOneById(id);
			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/uuid/{uuid}")
	public RestfulResponse getByUuid(@PathVariable String uuid) {
		try {
			Assets assets = assetsRepository.findOneByUuid(uuid);
			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}
	
	@GetMapping("/get/qrcode/{qrcode}")
	public RestfulResponse getByQrcode(@PathVariable String qrcode) {
		try {
			Assets assets = assetsRepository.findOneByQrcode(qrcode);
			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/get/transaction/{transactionId}")
	public RestfulResponse getByTransaction(@PathVariable String transactionId) {
		try {
			Assets assets = assetsRepository.findOneByTransactionId(transactionId);
			String message = messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, assets);
		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Assets assets) {
		try {
			logger.info("Upload {}", assets.toString());
			Assets tmp = assetsRepository.findOneById(assets.getId());
			if (tmp == null) {
				return new RestfulResponse(false, 0, messageSource.getMessage("assets.not.exist.false", null, LocaleContextHolder.getLocale()), null);
			}

			if (tmp.getStatus() == Examine.Approved) {
				return new RestfulResponse(false, 0, messageSource.getMessage("assets.status.approved.false", null, LocaleContextHolder.getLocale()), null);
			} else {
				if (assets.getUuid() != null) {
					tmp.setUuid(assets.getUuid().toLowerCase());
				}
				if (assets.getQrcode() != null) {
					tmp.setQrcode(assets.getQrcode());
				}
				if (assets.getMemberId() != null) {
					tmp.setMemberId(assets.getMemberId());
				}
				if (assets.getName() != null) {
					tmp.setName(assets.getName());
				}
				if (assets.getDescription() != null) {
					tmp.setDescription(assets.getDescription());
				}
				if (assets.getType() != null) {
					tmp.setType(assets.getType());
				}
				if (assets.getLocation() != null) {
					tmp.setLocation(assets.getLocation());
				}
				if (assets.getHypermedia() != null) {
					boolean thumbnail = true;
					for (Hypermedia hypermedia : assets.getHypermedia()) {

						if (thumbnail) {
							tmp.setThumbnail(hypermedia.getHash());
							thumbnail = false;
						}

						hypermediaRepository.save(hypermedia);
					}
					tmp.setHypermedia(assets.getHypermedia());
				}

				if (tmp.getUuid() != null && !tmp.getUuid().isEmpty()) {

					if (assets.getStatus() == Examine.Approved) {
						String transactionId = blockchainService.createBlockchainAssets(tmp.getUuid(), tmp);
						tmp.setTransactionId(transactionId);
						messageService.send(new Message(tmp.getMemberId(), "资产审核", String.format("恭喜您！%s 已经上链，HASH值是：%s。", tmp.getName(), transactionId)));
					} else if (assets.getStatus() == Examine.Rejected) {
						messageService.send(new Message(tmp.getMemberId(), "资产审核", String.format("很遗憾！%s 不符合上链标准，请完善资料再提交。", tmp.getName())));
					} else if (assets.getStatus() == Examine.Pending) {

						if (tmp.getUuid() != null && tmp.getQrcode() != null) {
							messageService.send(new Message(tmp.getMemberId(), "资产审核", String.format("%s 已经提交至艺术银行，请耐心等待。", tmp.getName())));
						} else {
							return new RestfulResponse(false, 0, messageSource.getMessage("assets.status.false", null, LocaleContextHolder.getLocale()), null);
						}
					}
					tmp.setStatus(assets.getStatus());
				}

				tmp.setUpdateDate(new Date());

				assetsRepository.save(tmp);

				logger.info("Upload {}", tmp.toString());
				return new RestfulResponse(true, 0, messageSource.getMessage("assets.update.true", null, LocaleContextHolder.getLocale()), tmp);
			}

		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("assets.update.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}
	}

	@GetMapping("/get/allinone/{id}")
	public RestfulResponse getAll(@PathVariable String id) {
		try {

			Assets assets = assetsRepository.findOneById(id);
			if (assets != null) {
				List<History> history = historyRepository.findByAssetsId(assets.getId());
				List<Certificate> certificate = certificateRepository.findByAssetsIdAndStatus(assets.getId(), "Yes");
				Organization organization = organizationRepository.findOneById(assets.getOrganizationId());
				List<Comment> comment = commentRepository.findAllByAssetsId(assets.getId());

				Map<String, Object> all = new LinkedHashMap<String, Object>();
				all.put("Assets", assets);
				all.put("History", history);
				all.put("Certificate", certificate);
				all.put("Organization", organization);
				all.put("Comment", comment);

				return new RestfulResponse(true, 0, messageSource.getMessage("assets.get.true", null, LocaleContextHolder.getLocale()), all);
			} else {
				return new RestfulResponse(true, 0, messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale()), null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("assets.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		try {
			Assets assets = assetsRepository.findOneById(id);
			if (assets.getStatus() == Examine.Approved) {
				return new RestfulResponse(true, 0, messageSource.getMessage("assets.delete.status.approved.false", null, LocaleContextHolder.getLocale()), null);
			} else {
				assetsRepository.deleteById(id);
				return new RestfulResponse(true, 0, messageSource.getMessage("assets.remove.true", null, LocaleContextHolder.getLocale()), null);
			}
		} catch (Exception e) {
			return new RestfulResponse(false, 0, messageSource.getMessage("assets.remove.false", null, LocaleContextHolder.getLocale()), e.getMessage());
		}
	}
}
