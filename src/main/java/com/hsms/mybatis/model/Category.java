package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class Category {
    private String ctgId;
    private String ctgName;
    private String parentCtgId;
    private Integer ctgLevel;
    private String ctgSort;
    private String gradeCode;
    private String subjectCode;
    private String useYn;


    @Override
    public String toString() {
        return "Category{" +
                "ctgId=" + ctgId +
                ", ctgName='" + ctgName + '\'' +
                ", parentCtgId='" + parentCtgId + '\'' +
                ", ctgLevel='" + ctgLevel + '\'' +
                ", ctgSort='" + ctgSort + '\'' +
                ", gradeCode=" + gradeCode +
                ", subjectCode=" + subjectCode +
                ", useYn=" + useYn +
                '}';
    }
}
