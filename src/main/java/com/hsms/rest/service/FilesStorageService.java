package com.hsms.rest.service;

import org.springframework.stereotype.Service;

@Service
public class FilesStorageService {

//	private static final Logger log = LoggerFactory.getLogger(FilesStorageService.class);
//	@Autowired
//	private FileService fileService;
//
//	private final String FILE_DATA_ROOT = "/ctc-work/HSMS/FILE_DATA";	

	

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
//	public void deleteAll(Path path) {
//		FileSystemUtils.deleteRecursively(path.toFile());
//	}
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
//	public void createDirectory(Path path) {
//		try {
//			Files.createDirectories(path);
//		} catch (IOException e) {
//		}
//	}
}
