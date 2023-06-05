package com.hsms.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	/**
	 * 디렉토리 생성
	 * 
	 * @param path
	 */
	public static void createDirectory(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
		}
	}
	
	public static void save(Path path, MultipartFile file) {
		InputStream in = null;
		try {
			in = file.getInputStream();
			Files.copy(in, path.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void save(Path path, Resource file) {
		InputStream in = null;
		try {
			in = file.getInputStream();
			Files.copy(in, path.resolve(file.getFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void deleteAll(Path path) {
		FileSystemUtils.deleteRecursively(path.toFile());
	}

	public static void copy(Path path, File file) {

		try {
			Resource resource = new UrlResource(file.toURI());
			copy(path, resource);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public static void copy(Path path, Resource file) {
		InputStream in = null;
		try {
			in = file.getInputStream();
			Files.copy(in, path.resolve(file.getFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
}
