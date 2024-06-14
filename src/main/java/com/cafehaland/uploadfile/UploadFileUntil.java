package com.cafehaland.uploadfile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MultipartFile;


public class UploadFileUntil {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileUntil.class);

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save file " + fileName, e);
		}
	}
	
	public static void clearDir(String dir) {
		Path pathDir = Paths.get(dir);
		try {
			Files.list(pathDir).forEach(
						file -> {
							try {
								Files.delete(file);
							} catch (IOException e) {
								//System.out.println("Could not delete file: " + file);
								LOGGER.error("Could not delete file: " + file);
							}
						}
					);
		} catch (IOException e) {
			//System.out.println("Could not list directory " + pathDir);
			LOGGER.error("Could not list directory " + pathDir);
		}
		
	}
}
