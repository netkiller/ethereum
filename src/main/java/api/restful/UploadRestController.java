package api.restful;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import api.domain.Multimedia;
import api.pojo.RestfulResponse;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

@RestController
@RequestMapping("/upload")
public class UploadRestController {

	private static final Logger logger = LoggerFactory.getLogger(UploadRestController.class);

	@Value("${ipfs.url:/ip4/127.0.0.1/tcp/5001}")
	private String url;

	@Value("${ipfs.path:/tmp}")
	private String folder;

	@Value("${upload.path:/tmp}")
	private String path;

	public UploadRestController() {
	}

	@PostMapping(value = "/file")
	public RestfulResponse file(@RequestParam("file") MultipartFile[] multipartFiles) {
		List<Multimedia> filelist = new ArrayList<Multimedia>();
		try {

			for (MultipartFile multipartFile : multipartFiles) {
				// File tmpfile = new File(this.getLocalfile(multipartFile.getOriginalFilename()));
				String filename = this.getUniqueFilename(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
				File tmpfile = new File(path + "/" + filename);
				String filepath = tmpfile.getPath();
				logger.info(filepath);
				multipartFile.transferTo(tmpfile);
				filelist.add(new Multimedia(multipartFile.getOriginalFilename(), filename, this.getExtensionName(multipartFile.getOriginalFilename())));
			}

			return new RestfulResponse(true, 0, null, filelist);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
	}

	@PostMapping("/ipfs/single")
	public RestfulResponse upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			return new RestfulResponse(false, 0, "Please select a file to upload", "");
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(this.getLocalfile(file.getOriginalFilename()));
			Files.write(path, bytes);
			return new RestfulResponse(true, 0, "", this.add(new File(path.toUri())));
		} catch (Exception e) {
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
	}

	@PostMapping(value = "/ipfs/group")
	public RestfulResponse group(@RequestParam("file") MultipartFile[] multipartFiles) {
		List<Object> filelist = new ArrayList<Object>();
		try {

			for (MultipartFile multipartFile : multipartFiles) {
				// File tmpfile = new File(this.getLocalfile(file.getOriginalFilename()));
				File tmpfile = new File(folder + "/" + this.getUniqueFilename(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
				String filepath = tmpfile.getPath();
				logger.info(filepath);
				multipartFile.transferTo(tmpfile);
				filelist.add(this.add(tmpfile));
			}
			return new RestfulResponse(true, 0, null, filelist);
		} catch (Exception e) {
			return new RestfulResponse(false, 0, e.getMessage(), null);
		}
	}

	private Object add(File file) throws IOException {
		IPFS ipfs = new IPFS(this.url);
		NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
		MerkleNode addResult = ipfs.add(fileWrapper).get(0);
		class Object {
			public String hash = addResult.hash.toBase58();
			public String name = addResult.name.get();

			@Override
			public String toString() {
				return "Object [hash=" + hash + ", name=" + name + "]";
			}
			// public Integer Size = addResult.size.get();

		}

		logger.info(addResult.toJSONString());
		return new Object();
	}

	private String getUniqueFilename(InputStream inputStream, String filename) throws IOException, NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA"); // SHA, MD2, MD5, SHA-256, SHA-384...
		// file hashing with DigestInputStream
		try (DigestInputStream dis = new DigestInputStream(inputStream, md)) {
			while (dis.read() != -1)
				; // empty loop to clear the data
			md = dis.getMessageDigest();
		}

		// bytes to hex
		StringBuilder result = new StringBuilder();
		for (byte b : md.digest()) {
			result.append(String.format("%02x", b));
		}

		String path = String.format("%s.%s", result.toString(), this.getExtensionName(filename));

		return path;

	}

	private String getLocalfile(String filename) {
		UUID uuid = UUID.randomUUID();
		String localfile = String.format("%s/%s.%s", folder, uuid.toString(), this.getExtensionName(filename));
		return localfile;
	}

	private String getExtensionName(String filename) {
		filename = filename.toLowerCase();
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
}