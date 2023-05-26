package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class Basket {

	private String userId;
	private String gradeCode;
	private String subjectCode;
	private String qstId;
	private String qstSort;
	private String inDate;
	private String upDate;
	@Override
    public String toString() {
        return "Basket{" +
                "userId=" + userId +
                "gradeCode=" + gradeCode +
                "subjectCode=" + subjectCode +
                "qstId=" + qstId +
                "qstSort=" + qstSort +
                "inDate=" + inDate +
                "upDate=" + upDate +
                '}';
    }
}
