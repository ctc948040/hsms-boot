package com.hsms.rest.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import com.hsms.mybatis.model.ComModel;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;
import com.hsms.rest.service.ComService;
import com.hsms.rest.service.FileService;
import com.hsms.rest.service.QuestionService;
import com.hsms.util.CompressZip;
import com.hsms.util.ExcelGenerator;
import com.hsms.util.ExcelReader;
import com.hsms.util.FileUtils;
import com.hsms.util.UnZip;
import com.hsms.util.dateUtils;

@RestController
@RequestMapping("/question")
public class QuestionController {
//	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	private QuestionService questionService;
	
	@Autowired
    private FileService fileService;

	@Autowired
	private ComService comService;


	private final String EXPORT_ROOT = "/efs/hsms/WORK_TEMP/export";
	private final String IMPORT_ROOT = "/efs/hsms/WORK_TEMP/import";

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("/selectListQuestion")
	public ResponseEntity<DefaultRes<List<Question>>> selectListQuestion(Question question) {

		return new ResponseEntity<DefaultRes<List<Question>>>(questionService.selectListQuestion(question),
				HttpStatus.OK);
	}

	@GetMapping("/export-to-excel")
	public ResponseEntity<Object> exportIntoExcelFile(Question question) throws IOException {

//		UnZip unZip = new UnZip();
//		// 압축 해제 
//		if (!unZip.unZip(EXPORT_ROOT+"/", "question.zip", EXPORT_ROOT+"/")) {
//			System.out.println("압축 해제 실패");
//		}

		ComModel comModel = comService.selectUuid();
		String uuid = comModel.getUuid();
		Path tempPath = Paths.get(EXPORT_ROOT + "/" + uuid);
		FileUtils.createDirectory(tempPath);
		try {			
			String currentDateTime = dateUtils.getDateString("yyyyMMddHHmmss");
			question.setAllYn("Y");
			DefaultRes<List<Question>> dr = questionService.selectListQuestion(question);
			List<Question> list = dr.getData();

			for (Question qst : list) {
				Path ctgPath = Paths.get(tempPath.toFile().getPath(), qst.getCtgName());
				FileUtils.createDirectory(ctgPath);
				fileService.copyFile(qst.getQstFileId(), ctgPath);
				fileService.copyFile(qst.getCmntFileId(), ctgPath);
			}

			ObjectMapper objectMapper = new ObjectMapper();

			@SuppressWarnings("unchecked")
			List<Map<String, String>> listMap = objectMapper.convertValue(list, List.class);

			List<String> keyList = Arrays.asList(new String[] { "ctgId", "ctgName", "qstId", "qstTitle", "qstPattern",
					"answer", "comment", "qstFileId", "qstFileName", "cmntFileId", "cmntFileName", "qstTypeCode",
					"qstTypeName", "answerKindCode", "answerKindName", "displayTypeCode", "displayTypeName",
					"dfcltLevelCode", "dfcltLevelName" });
			List<String> displayList = Arrays.asList(new String[] { "카테고리아이디[ctgId]", "카테고리명[ctgName]", "문제아이디[qstId]",
					"문제타이틀[qstTitle]", "문제유형[qstPattern]", "답안[answer]", "해설[comment]", "문제파일아이디[qstFileId]",
					"문제파일명[qstFileName]", "해설파일아이디[cmntFileId]", "해설파일명[cmntFileName]", "문제타입코드[qstTypeCode]",
					"문제타입명[qstTypeName]", "답유형코드[answerKindCode]", "답유형명[answerKindName]", "표시타입코드[displayTypeCode]",
					"표시타입명[displayTypeName]", "난이도코드[dfcltLevelCode]", "난이도명[dfcltLevelName]" });

			ExcelGenerator generator = new ExcelGenerator(keyList, displayList, listMap);

			String filename = "question_" + currentDateTime + ".xlsx";
			File excelFile = new File(tempPath.toFile(), filename);

			generator.saveExcelFile(excelFile);

			CompressZip compressZip = new CompressZip();

			// 압축 하기
			String sourcePath = tempPath.toFile().getPath();
			String targetPath = tempPath.toFile().getParent() + "/";
			String targetFilename = "question_" + uuid + ".zip";
			if (!compressZip.compress(sourcePath, targetPath, targetFilename)) {
				System.out.println("압축 실패");
			}

			Path zipPath = Paths.get(targetPath + targetFilename);

			Resource resource = new UrlResource(zipPath.toUri());
			HttpHeaders headers = new HttpHeaders();
//			String filename = URLEncoder.encode(resource.getFilename(), "utf-8");
//			filename = filename.replaceAll("\\+", "%20");
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(targetFilename).build()); // 다운로드

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		} finally {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			FileUtils.deleteAll(tempPath);
		}
	}
	
	@PostMapping("/importExcel")
	public ResponseEntity<DefaultRes<List<Question>>> importExcel(
			@RequestParam("file") MultipartFile[] files) {
		Path tempPath = null;
		try {
			ComModel comModel = comService.selectUuid();
			 String uuid = comModel.getUuid();
			 String tempPathStr= IMPORT_ROOT+"/" +uuid;
			 tempPath = Paths.get(tempPathStr);
			 FileUtils.createDirectory(tempPath);
			 
			 String fileName = files[0].getOriginalFilename();
			 
			 if(!fileName.endsWith(".zip")) {
				 throw new RuntimeException("zip을 업로드 해주세요.");
			 }
			 
			 FileUtils.save(tempPath,files[0]);
			 
			 UnZip unZip = new UnZip();
				// 압축 해제 
			if (!unZip.unZip(tempPathStr+"/", fileName, tempPathStr+"/")) {
				throw new RuntimeException("압축 해제 실패");
			}
			
			String unzipDir = tempPathStr + "/" + fileName.replaceAll(".zip", "");
			File unzipDirFile = new File(unzipDir);
			
			File[] fileNameList = unzipDirFile.listFiles(new FilenameFilter() { 
	            
	            @Override 
	            public boolean accept(File dir, String name) { 
	                 return name.endsWith(".xlsx");
	            }
			});
			
			if(fileNameList.length != 1) {
				throw new RuntimeException("하나의 엑셀만 업로드 해주세요.");
			}
			
			Path excelPath = fileNameList[0].toPath();
			
			Resource resource = new UrlResource(excelPath.toUri());
			
			List<Map<String, String>> list = ExcelReader.read(resource);
			ObjectMapper objectMapper = new ObjectMapper();
			
			TypeReference<List<Question>> tr = new TypeReference<List<Question>>() {};
			
			List<Question> questionList = objectMapper.convertValue(list, tr);
			
			questionList = questionService.importQuestion(questionList,unzipDirFile);
			
			DefaultRes<List<Question>> res = DefaultRes.res(StatusCode.OK, ResponseMessage.WRITE_QUESTION, questionList);
			return new ResponseEntity<DefaultRes<List<Question>>>(res, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<DefaultRes<List<Question>>>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), null), HttpStatus.CONFLICT);
		} finally {
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			FileUtils.deleteAll(tempPath);
		}
	}
}