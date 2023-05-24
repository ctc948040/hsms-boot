package com.hsms.rest.controller;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.FileModel;
import com.hsms.rest.service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/file")
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	 private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
	
	@GetMapping("/download")
	public ResponseEntity<Object> download(FileModel fileModel, HttpServletResponse response) {
		String rootPath = "/ctc-work/HSMS/FILE_DATA";
		
		try {
			FileModel retFileModel = fileService.selectFile(fileModel);
			
			String filePathStr = rootPath + retFileModel.getFilePath()+"/"+retFileModel.getFileId();
			
			log.info("{}",retFileModel);
			log.info(filePathStr);
			
			Path filePath = Paths.get(filePathStr);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
//			
			HttpHeaders headers = new HttpHeaders();
			String filename = URLEncoder.encode(retFileModel.getFileName(), "utf-8");
			filename = filename.replaceAll("\\+", "%20");
	         
//			response.setContentType("application/download; utf-8");
			
			//headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
}