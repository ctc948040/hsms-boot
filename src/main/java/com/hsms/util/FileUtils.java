package com.hsms.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.util.FileSystemUtils;

public class FileUtils {
	/**
	   * 디렉토리 생성
	   * @param path
	   */
	  public static void createDirectory(Path path) {
		  try {
		      Files.createDirectories(path);
		    } catch (IOException e) {
		    }
	  }
	  
	  public static void deleteAll(Path path) {
	    FileSystemUtils.deleteRecursively(path.toFile());
	  }
}
