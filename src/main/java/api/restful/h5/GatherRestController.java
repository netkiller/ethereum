package api.restful.h5;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api.domain.h5.Gather;
import api.repository.h5.GatherRepository;
import api.service.UploadService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/h5/gather")
public class GatherRestController {
	private static final Logger logger = LoggerFactory.getLogger(GatherRestController.class);

	@Value("${upload.path:/tmp}")
	private String path;

	private final String folder = "gather";

	@Autowired
	private UploadService uploadService;

	@Autowired
	private GatherRepository gatherRepository;

	public GatherRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/test")
	public String test() {
		return "Helloworld!!!";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("img_file") MultipartFile multipartFile) {

		try {
			String filename = uploadService.getUniqueFilename(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			String url = this.folder + "/" + filename;

			File tmpdir = new File(path + "/" + this.folder);
			if (!tmpdir.isDirectory()) {
				tmpdir.mkdir();
			}

			File tmpfile = new File(path + "/" + this.folder + "/" + filename);
			String filepath = tmpfile.getPath();
			logger.info(filepath);
			multipartFile.transferTo(tmpfile);
			return url;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@PostMapping("/save")
	public String save(@RequestParam(value = "method", required = false) int method, @RequestParam(value = "money", required = false) String money, @RequestParam(value = "age", required = false) int age, @RequestParam(value = "describe", required = false) String describe, @RequestParam(value = "source", required = false) String source, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "tel", required = false) String tel, @RequestParam(value = "area", required = false) String area, @RequestParam(value = "picture[]", required = false) String[] picture, @RequestParam(value = "address", required = false) String address) {

		String methods[] = { "移交", "捐赠", "征购" };
		String ages[] = { "1840年之前", "1840年-1949年", "新中国成立以来", "新时代(十八大以来)" };

		Gather gather = new Gather();
		gather.setMethod(methods[method - 1]);
		gather.setMoney(money);
		gather.setAge(ages[age - 1]);
		gather.setDescription(describe);
		gather.setSource(source);
		gather.setName(name);
		gather.setTel(tel);
		gather.setArea(area);
		gather.setAddress(address);
		if (picture != null) {
			gather.setPicture(String.join(",", picture));
		}
		logger.info(gather.toString());

		gatherRepository.save(gather);
		return "{status:1}";
	}
}
