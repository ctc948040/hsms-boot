package com.hsms.rest.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsms.mybatis.mapper.ComMapper;
import com.hsms.mybatis.model.ComModel;
import com.hsms.mybatis.model.FileModel;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.FilesStorageService;
import com.hsms.rest.service.QuestionService;
import com.hsms.util.CompressZip;
import com.hsms.util.ExcelGenerator;
import com.hsms.util.FileUtils;
import com.hsms.util.UnZip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/question")
public class QuestionController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	private QuestionService questionService;

	@Autowired
	private ComMapper comMapper;

	@Autowired
	private FilesStorageService filesStorageService;

	private final String EXPORT_ROOT = "/ctc-work/HSMS/WORK_TEMP/export";

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

		ComModel comModel = comMapper.selectUuid();
		String uuid = comModel.getUuid();
		Path tempPath = Paths.get(EXPORT_ROOT + "/" + uuid);
		FileUtils.createDirectory(tempPath);
		try {

			DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentDateTime = dateFormatter.format(new Date());
			question.setAllYn("Y");
			DefaultRes<List<Question>> dr = questionService.selectListQuestion(question);
			List<Question> list = dr.getData();

			for (Question qst : list) {
				Path ctgPath = Paths.get(tempPath.toFile().getPath(), qst.getCtgName());
				FileUtils.createDirectory(ctgPath);
				filesStorageService.copyFile(qst.getQstFileId(), ctgPath);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileUtils.deleteAll(tempPath);
		}
	}
}