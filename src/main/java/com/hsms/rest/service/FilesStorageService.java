package com.hsms.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageService {	  

	  public void save(Path path,MultipartFile file)  {		  
		  InputStream in = null;
	    try {
	    	 in = file.getInputStream();
	      Files.copy(in, path.resolve(file.getOriginalFilename()));
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    } finally {
	    	try {
				if(in != null) in.close();
			} catch (IOException e) {
			}
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
	   * @param path
	   */
	  public void createDirectory(Path path) {
		  try {
		      Files.createDirectories(path);
		    } catch (IOException e) {
		    }
	  }
}
