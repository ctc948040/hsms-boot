package com.hsms.mybatis.model;

import java.util.List;

import lombok.Data;

@Data
public class Category {
    private String ctgId;
    private String ctgName;
    private String parentCtgId;
    private String parentCtgName;
    private String ctgLevel;
    private String ctgSort;
    private String ctgSort_;
    private String gradeCode;
    private String gradeName;
    private String subjectCode;
    private String subjectName;
    private String useYn;
    private String label;
    private String id;
    private String avatar;
    private String icon;
    private String color;
    private boolean lazy;
    private List<Category> children;


    @Override
    public String toString() {
        return "Category{" +
                "label=" + label +
                "id=" + id +
                "avatar=" + avatar +
                "icon=" + icon +
                "color=" + color +
                "lazy=" + lazy +
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
