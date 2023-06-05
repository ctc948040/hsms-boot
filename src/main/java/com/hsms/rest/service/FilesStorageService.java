package com.hsms.rest.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hsms.mybatis.model.FileModel;
import com.hsms.rest.controller.QuestionController;

import jdk.internal.org.jline.utils.Log;

@Service
public class FilesStorageService {

	private static final Logger log = LoggerFactory.getLogger(FilesStorageService.class);
	@Autowired
	private FileService fileService;

	private final String FILE_DATA_ROOT = "/ctc-work/HSMS/FILE_DATA";

	public void save(Path path, MultipartFile file) {
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

	public void save(Path path, Resource file) {
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

	public void copyFile(String qstFileId, Path tempPath) {
		FileModel fileModel = new FileModel();
		fileModel.setFileId(qstFileId);
		FileModel retFileModel = fileService.selectFile(fileModel);

		String orgFilePathStr = retFileModel.getFilePath();
		String orgFileName = retFileModel.getFileName();
		Path filePath = Paths.get(FILE_DATA_ROOT + orgFilePathStr + "/" + qstFileId);
		try {
			Resource file = new UrlResource(filePath.toUri());
			this.save(tempPath, file);
			File copyFile = new File(tempPath.toFile(), qstFileId);
			copyFile.renameTo(new File(copyFile.getParent(), orgFileName));
		} catch (Exception e) {
			throw new RuntimeException("copyFile error");
		}

	}

//	  public Resource load(String filename) {
//	    try {
//	      Path file = root.resolve(filename);
//	      Resource resource = new UrlResource(file.toUri());
//
//	      if (resource.exists() || resource.isReadable()) {
//	        return resource;
//	      } else {
//	        throw new RuntimeException("Could not read the file!");
//	      }
//	    } catch (MalformedURLException e) {
//	      throw new RuntimeException("Error: " + e.getMessage());
//	    }
//	  }
//
	public void deleteAll(Path path) {
		FileSystemUtils.deleteRecursively(path.toFile());
	}
//
//	  public Stream<Path> loadAll() {
//	    try {
//	      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
//	    } catch (IOException e) {
//	      throw new RuntimeException("Could not load the files!");
//	    }
//	  }

	/**
	 * 디렉토리 생성
	 * 
	 * @param path
	 */
	public void createDirectory(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
		}
	}
}
