package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class Question {

	private String qstId;
	private String qstTitle;
	private String qstPattern;
	private String answer;
	private String comment;
	private String ctgId;
	private String ctgName;
	private String qstFileId;
	private String qstFileName;
	private String cmntFileId;
	private String cmntFileName;
	private String qstTypeCode;//문제타입코드(이미지,직접작성)
	private String qstTypeName;//문제타입명
	private String answerKindCode;//답유형코드(객관식,주관식,서술형)
	private String answerKindName;//답유형명
	private String displayTypeCode;//표시타입코드(이미지,텍스트)
	private String displayTypeName;//표시타입명
	private String dfcltLevelCode;
	private String dfcltLevelName;
	private String importState;
	private String gradeCode;
	private String subjectCode;
	
	private String pageNum;
	private Integer cnt;
	private String allYn;
	@Override
    public String toString() {
        return "Question{" +
                "qstId=" + qstId +
                "qstTitle=" + qstTitle +
                "qstPattern=" + qstPattern +
                "qstPattern=" + answer +
                "qstPattern=" + comment +
                "ctgId=" + ctgId +
                "ctgName=" + ctgName +
                "qstFileId=" + qstFileId +
                "qstFileName=" + qstFileName +
                "cmntFileId=" + cmntFileId +
                "cmntFileName=" + cmntFileName +
                "qstTypeCode=" + qstTypeCode +
                "qstTypeName=" + qstTypeName +
                "answerKindCode=" + answerKindCode +
                "answerKindName=" + answerKindName +
                "displayTypeCode=" + displayTypeCode +
                "displayTypeName=" + displayTypeName +
                "dfcltLevelCode=" + dfcltLevelCode +
                "importState=" + importState +
                '}';
    }
}
