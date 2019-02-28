package api.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import api.service.UploadService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

@Service
public class UploadServiceImpl implements UploadService {

	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Value("${ipfs.url:/ip4/127.0.0.1/tcp/5001}")
	private String url;

	@Value("${ipfs.path:/tmp}")
	private String folder;

	@Value("${upload.path:/tmp}")
	private String path;

	public UploadServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public Object add(File file) throws IOException {
		IPFS ipfs = new IPFS(this.url);
		NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
		MerkleNode addResult = ipfs.add(fileWrapper).get(0);
		class Object {
			public String Hash = addResult.hash.toBase58();
			public String Name = addResult.name.get();

			// public Integer Size = addResult.size.get();
			@Override
			public String toString() {
				return "Object [Hash=" + Hash + ", Name=" + Name + "]";
			}

		}

		logger.info(addResult.toJSONString());
		return new Object();
	}

	public String getUniqueFilename(InputStream inputStream, String filename) {

		try {

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	public String getLocalfile(String filename) {
		UUID uuid = UUID.randomUUID();
		String localfile = String.format("%s/%s.%s", folder, uuid.toString(), this.getExtensionName(filename));
		return localfile;
	}

	public String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

}
