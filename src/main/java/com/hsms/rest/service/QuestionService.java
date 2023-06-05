package com.hsms.rest.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.QuestionMapper;
import com.hsms.mybatis.model.Category;
import com.hsms.mybatis.model.ComModel;
import com.hsms.mybatis.model.FileModel;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;

@Service
public class QuestionService {
//	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    private QuestionMapper questionMapper;
    @Autowired
    private FileService fileService;
    
    @Autowired
	private ComService comService;
    
    
    
    @Autowired
    private CategoryService categoryService;

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

	public List<Question> importQuestion(List<Question> questionList, File unzipDirFile) {
		
		for(Question q : questionList) {
			
			try {
				Category category = new Category();
				category.setCtgId(q.getCtgId());
				category = categoryService.selectCategory(category);
				
				String gradeCode = category.getGradeCode();
				String subjectCode = category.getSubjectCode();
				String qstFileName = q.getQstFileName();
				String cmntFileName = q.getCmntFileName();
				String ctgName = q.getCtgName();
				
				//문제 파일
				FileModel fileModel1 = fileService.insertFile(unzipDirFile,gradeCode,subjectCode,ctgName,qstFileName);
				if(StringUtils.hasLength(fileModel1.getFileId())) q.setQstFileId(fileModel1.getFileId());
				
				//해설 파일
				FileModel fileModel2 = fileService.insertFile(unzipDirFile,gradeCode,subjectCode,ctgName,cmntFileName);
				if(StringUtils.hasLength(fileModel2.getFileId())) q.setCmntFileId(fileModel2.getFileId());
				
				if(!StringUtils.hasLength(q.getQstId())) {
					ComModel comModel = comService.selectUuid("QST");
					q.setQstId(comModel.getUuid());				
					q.setImportState("추가");
				} else {
					q.setImportState("변경");
				}
				
				questionMapper.insertQuestion(q);
			} catch (Exception e) {
				e.printStackTrace();
				q.setImportState("에러 : "+e.getMessage());
			}
			
		}//for
		
		return questionList;
	}
}
