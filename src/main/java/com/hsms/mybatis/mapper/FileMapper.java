package com.hsms.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hsms.mybatis.model.FileModel;

/**
 * @author c.t.c
 * @date 2018/12/20
 */
@Mapper
public interface FileMapper {
    
	FileModel selectFile(FileModel fileModel);
}
