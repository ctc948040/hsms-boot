package com.hsms.rest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.QuestionMapper;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;

@Service
public class QuestionService {
//	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    private QuestionMapper questionMapper;

    public QuestionService(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    // 카테고리 조회
    public DefaultRes<List<Question>> selectListQuestion(Question question) {
    	
    	
        List<Question> questionList = questionMapper.selectListQuestion(question);
        
        if (questionList.isEmpty()) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_QUESTION);
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_QUESTION, questionList);
    }
}
