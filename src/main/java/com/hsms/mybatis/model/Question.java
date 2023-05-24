package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class Question {

	private String qstId;
	private String qstTitle;
	private String qstPattern;
	private String ctgId;
	private String qstFileId;
	private String cmntFileId;
	private String answerTypeCode;
	private String commentTypeCode;
	private String dfcltLevelCode;
	private String pageNum;
	@Override
    public String toString() {
        return "Question{" +
                "qstId=" + qstId +
                "qstTitle=" + qstTitle +
                "qstPattern=" + qstPattern +
                "ctgId=" + ctgId +
                "qstFileId=" + qstFileId +
                "cmntFileId=" + cmntFileId +
                "answerTypeCode=" + answerTypeCode +
                "commentTypeCode=" + commentTypeCode +
                "dfcltLevelCode=" + dfcltLevelCode +
                '}';
    }
}
