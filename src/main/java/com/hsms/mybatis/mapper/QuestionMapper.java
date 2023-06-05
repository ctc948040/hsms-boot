package com.hsms.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsms.mybatis.model.Question;

/**
 * @author c.t.c
 * @date 2018/12/20
 */
@Mapper
public interface QuestionMapper {
    
//	Question selectFile(Question question);

	List<Question> selectListQuestion(Question question);

	void insertQuestion(Question question);
}
