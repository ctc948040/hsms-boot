package com.hsms.rest.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsms.ResponseMessage;
import com.hsms.mybatis.model.Category;
import com.hsms.mybatis.model.FileModel;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;
import com.hsms.rest.service.CategoryService;
import com.hsms.rest.service.FileService;
import com.hsms.util.ExcelReader;

@RestController
@RequestMapping("/file")
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
//	@Autowired
//	private ComMapper comMapper;
	private FileService fileService;
	@Autowired
	private CategoryService categoryService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	@PostMapping("/importExcel")
	public ResponseEntity<DefaultRes<List<Category>>> importExcel(
			@RequestParam("file") MultipartFile[] files) {

		List<Map<String, String>> list = ExcelReader.read(files[0]);
		ObjectMapper objectMapper = new ObjectMapper();
		
		TypeReference<List<Category>> tr = new TypeReference<List<Category>>() {};
		
		List<Category> categoryList = objectMapper.convertValue(list, tr);
		
		categoryList = categoryService.importCategory(categoryList);
		
		DefaultRes<List<Category>> res = DefaultRes.res(StatusCode.OK, ResponseMessage.WRITE_CATEGORY, categoryList);
		return new ResponseEntity<DefaultRes<List<Category>>>(res, HttpStatus.OK);
	}

	@GetMapping("/download")
	public ResponseEntity<Object> download(FileModel fileModel, HttpServletResponse response) {
		String rootPath = "/efs/hsms/FILE_DATA";

		try {
			FileModel retFileModel = fileService.selectFile(fileModel);

			String filePathStr = rootPath + retFileModel.getFilePath() + "/" + retFileModel.getFileId();

			log.info("{}", retFileModel);
			log.info(filePathStr);

			Path filePath = Paths.get(filePathStr);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
			HttpHeaders headers = new HttpHeaders();
			String filename = URLEncoder.encode(retFileModel.getFileName(), "utf-8");
			filename = filename.replaceAll("\\+", "%20");

			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build()); 

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
}