package com.hsms.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
//	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	 private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
	
	@GetMapping("/selectListQuestion")
	public ResponseEntity<DefaultRes<List<Question>>> selectListQuestion(Question question) {
		
		return new ResponseEntity<DefaultRes<List<Question>>>(questionService.selectListQuestion(question), HttpStatus.OK);
	}
}