package api.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.History;
import api.pojo.RestfulResponse;
import api.repository.HistoryRepository;

@RestController
@RequestMapping("/history")
public class HistoryRestController {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HistoryRepository historyRepository;

	public HistoryRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public History json() {
		History history = new History();
		history.setStatus("录入");
		history.setTitle("添加信息");
		history.setMessage("信息内容");
		history.setCreateDate(new Date());
		history.setUpdateDate(null);
		return history;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody History history) {
		try {
			history.setCreateDate(new Date());
			history.setUpdateDate(null);
			historyRepository.save(history);
		} catch (Exception e) {
			String message = messageSource.getMessage("history.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("history.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(true, 0, message, history);
	}

	@GetMapping("/list/{assetsId}")
	public RestfulResponse list(@PathVariable String assetsId) {
		try {
			List<History> history = historyRepository.findByAssetsId(assetsId);
			String message = messageSource.getMessage("history.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, history);
		} catch (Exception e) {
			String message = messageSource.getMessage("history.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {
		try {
			historyRepository.deleteById(id);
			String message = messageSource.getMessage("history.remove.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(true, 0, message, null);
		} catch (Exception e) {
			String message = messageSource.getMessage("history.remove.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	public static List<String> list = new ArrayList<String>();
	static {
		list.add("录入");
		list.add("变更");
		list.add("收藏");
		list.add("在售");
		list.add("展出");
	}

	@GetMapping("/status/list")
	public RestfulResponse status() {
		return new RestfulResponse(true, 0, null, list);
	}
}
