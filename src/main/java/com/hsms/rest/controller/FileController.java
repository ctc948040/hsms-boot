package com.hsms.rest.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.ComMapper;
import com.hsms.mybatis.model.Category;
import com.hsms.mybatis.model.ComModel;
import com.hsms.mybatis.model.FileModel;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;
import com.hsms.rest.service.CategoryService;
import com.hsms.rest.service.FileService;
import com.hsms.rest.service.FilesStorageService;
import com.hsms.util.ExcelReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/file")
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private ComMapper comMapper;
	private FileService fileService;
	@Autowired
	private CategoryService categoryService;

	private FilesStorageService storageService;

	private final String UPLOAD_ROOT = "/ctc-work/HSMS/WORK_TEMP/uploads";

	public FileController(FileService fileService, FilesStorageService storageService) {
		this.fileService = fileService;
		this.storageService = storageService;
	}

//    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET}, value = "importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(value = "uploadFiles", tags = "uploadFiles")
	@PostMapping("/importExcel")
	public ResponseEntity<DefaultRes<List<Category>>> importExcel(
			@RequestParam("file") MultipartFile[] files) {

//    	log.info("{}",files);
		// ComModel comModel = comMapper.selectUuid();

		// String uuid = comModel.getUuid();

		// Path tempPath = Paths.get(UPLOAD_ROOT+"/" +uuid);
		// storageService.createDirectory(tempPath);/

//			Arrays.asList(files).stream().forEach(file -> {
////				storageService.save(tempPath,file);
////				fileNames.add(file.getOriginalFilename());
//			});
		List<Map<String, String>> list = ExcelReader.read(files[0]);
		ObjectMapper objectMapper = new ObjectMapper();
		
		TypeReference<List<Category>> tr = new TypeReference<List<Category>>() {};
		
		List<Category> categoryList = objectMapper.convertValue(list, tr);
		
		categoryList = categoryService.importCategory(categoryList);
		
		DefaultRes<List<Category>> res = DefaultRes.res(StatusCode.OK, ResponseMessage.WRITE_CATEGORY, categoryList);
		return new ResponseEntity<DefaultRes<List<Category>>>(res, HttpStatus.OK);

//			return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CATEGORY, ExcelReader.read(files[0]));
//			System.out.println(fileNames);
//			message = "Uploaded the files successfully: " + fileNames;
//			return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/download")
	public ResponseEntity<Object> download(FileModel fileModel, HttpServletResponse response) {
		String rootPath = "/ctc-work/HSMS/FILE_DATA";

		try {
			FileModel retFileModel = fileService.selectFile(fileModel);

			String filePathStr = rootPath + retFileModel.getFilePath() + "/" + retFileModel.getFileId();

			log.info("{}", retFileModel);
			log.info(filePathStr);

			Path filePath = Paths.get(filePathStr);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
//			
			HttpHeaders headers = new HttpHeaders();
			String filename = URLEncoder.encode(retFileModel.getFileName(), "utf-8");
			filename = filename.replaceAll("\\+", "%20");

//			response.setContentType("application/download; utf-8");

			// headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build()); // 다운로드
																												// 되거나
																												// 로컬에
																												// 저장되는
																												// 용도로
																												// 쓰이는지를
																												// 알려주는
																												// 헤더

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
}