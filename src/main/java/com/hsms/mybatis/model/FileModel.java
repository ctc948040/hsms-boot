package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class FileModel {
	
	private String fileId;
	private String fileName;
	private String filePath;
	private String regUserId;
	private String inDate;
	private String upDate;
	
	@Override
    public String toString() {
        return "FileModel{" +
                "fileId=" + fileId +
                "fileName=" + fileName +
                "filePath=" + filePath +
                "regUserId=" + regUserId +
                "inDate=" + inDate +
                "upDate=" + upDate +
                '}';
    }
}
