package com.hsms.rest.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.QuestionService;
import com.hsms.util.ExcelGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/question")
public class QuestionController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	 private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
	
	@GetMapping("/selectListQuestion")
	public ResponseEntity<DefaultRes<List<Question>>> selectListQuestion(Question question) {
		
		return new ResponseEntity<DefaultRes<List<Question>>>(questionService.selectListQuestion(question), HttpStatus.OK);
	}
	
	@GetMapping("/export-to-excel")
    public void exportIntoExcelFile(Question question,HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=question_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        question.setAllYn("Y");
        DefaultRes<List<Question>> dr = questionService.selectListQuestion(question);
        List<Question> list = dr.getData();
        
        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
		List<Map<String,String>> listMap = objectMapper.convertValue(list, List.class);
        
        log.info("{}",listMap);
        
        List<String> keyList = Arrays.asList(new String[]{"ctgId","ctgName","qstId","qstTitle","qstPattern","qstFileId","fileName","dfcltLevelName"});


        ExcelGenerator generator = new ExcelGenerator(keyList,listMap);
        generator.generateExcelFile(response);
    }
}