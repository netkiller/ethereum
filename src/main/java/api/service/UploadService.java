package api.service;

import java.io.InputStream;

public interface UploadService {

	String getUniqueFilename(InputStream inputStream, String originalFilename);

}
