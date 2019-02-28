package api.restful;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.pojo.RestfulResponse;

@RestController
@RequestMapping("/misc")
public class MiscRestController {

	public MiscRestController() {
		// TODO Auto-generated constructor stub
	}

	@Cacheable("misc:nfc:compatibility")
	@GetMapping("/nfc/compatibility")
	public RestfulResponse compatibility() {
		List<String> list = new ArrayList<String>();
		list.add("Iphone X, Iphone 8 plus");
		list.add("Iphone 7, Iphone 7 plus");
		list.add("小米2A, 小米3, 小米5");
		list.add("三星 S系列，A系列，Note系列");
		list.add("华为6高配版，荣耀6PLUS， MATE7，荣耀V8，MATE8，MATES");
		list.add("魅族Pro 5");
		list.add("HTC 10 lifestype");
		list.add("酷派锋尚3");
		list.add("Moto X级");
		list.add("Vivo X5 Pro");
		list.add("Oppo Find7");
		return new RestfulResponse(true, 0, "", list);
	}

	@Cacheable("misc:industry")
	@GetMapping("/industry")
	public RestfulResponse industry() {
		List<String> list = new ArrayList<String>();
		list.add("艺术品溯源");
		list.add("食品安全溯源");
		list.add("药品安全溯源");
		list.add("司法鉴定存证");
		list.add("金融票据");
		return new RestfulResponse(true, 0, "", list);
	}

	@Cacheable("misc:nfc:tag")
	@GetMapping("/nfc/tag")
	public RestfulResponse nfctag() {
		List<String> list = new ArrayList<String>();
		list.add("易碎纸标签");
		list.add("PVC便签");
		list.add("屯扎带便签");
		list.add("普通标签");
		list.add("定制标签");
		return new RestfulResponse(true, 0, "", list);
	}

	@Cacheable("misc:art:tag")
	@GetMapping("/art/tag")
	public RestfulResponse tag() {
		return new RestfulResponse(true, 0, "", Arrays.asList("青铜器", "书画", "玉器", "陶瓷", "杂项"));
	}
}
