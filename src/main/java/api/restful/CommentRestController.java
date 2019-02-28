package api.restful;

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

import api.domain.Comment;
import api.pojo.RestfulResponse;
import api.pojo.Enum.Status;
import api.repository.CommentRepository;

@RestController
@RequestMapping("/comment")
public class CommentRestController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MessageSource messageSource;

	public CommentRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/json")
	public Comment json() {
		Comment comment = new Comment();
		comment.setAssetsId("0000");
		comment.setMemberId("000");
		comment.setMessage("这件艺术品不错");
		comment.setStatus(Status.N);
		comment.setCreateDate(new Date());
		return comment;
	}

	@PostMapping("/create")
	public RestfulResponse create(@RequestBody Comment comment) {
		try {
			commentRepository.save(comment);
		} catch (Exception e) {
			String message = messageSource.getMessage("comment.create.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
		String message = messageSource.getMessage("comment.create.true", null, LocaleContextHolder.getLocale());
		return new RestfulResponse(false, 0, message, comment);
	}

	@PostMapping("/update")
	public RestfulResponse update(@RequestBody Comment comment) {
		try {
			Comment tmp = commentRepository.findOneById(comment.getId());
			tmp.setMessage(comment.getMessage());
			tmp.setUpdateDate(new Date());
			commentRepository.save(comment);

			String message = messageSource.getMessage("comment.update.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, tmp);
		} catch (Exception e) {
			String message = messageSource.getMessage("comment.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/list/{assetsId}")
	public RestfulResponse update(@PathVariable String assetsId) {
		try {
			List<Comment> tmp = commentRepository.findAllByAssetsId(assetsId);
			String message = messageSource.getMessage("comment.get.true", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, tmp);
		} catch (Exception e) {
			String message = messageSource.getMessage("comment.get.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

	@GetMapping("/remove/{id}")
	public RestfulResponse remove(@PathVariable String id) {

		try {
			Comment tmp = commentRepository.findOneById(id);
			if (tmp.getStatus() == Status.N) {
				commentRepository.deleteById(id);
				String message = messageSource.getMessage("comment.update.true", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, message, tmp);
			} else {
				String message = messageSource.getMessage("comment.update.false", null, LocaleContextHolder.getLocale());
				return new RestfulResponse(false, 0, message, null);
			}

		} catch (Exception e) {
			String message = messageSource.getMessage("comment.update.false", null, LocaleContextHolder.getLocale());
			return new RestfulResponse(false, 0, message, e.getMessage());
		}
	}

}
