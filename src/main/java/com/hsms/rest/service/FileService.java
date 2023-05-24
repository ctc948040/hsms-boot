package com.hsms.rest.service;

import org.springframework.stereotype.Service;

import com.hsms.mybatis.mapper.FileMapper;
import com.hsms.mybatis.model.FileModel;

@Service
public class FileService {
//	private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // 파일 단건 조회
    public FileModel selectFile(FileModel fileModel) {    	
    	
    	return fileMapper.selectFile(fileModel);
    }
}