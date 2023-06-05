package com.hsms.rest.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hsms.mybatis.mapper.FileMapper;
import com.hsms.mybatis.model.ComModel;
import com.hsms.mybatis.model.FileModel;
import com.hsms.util.FileUtils;
import com.hsms.util.dateUtils;

@Service
public class FileService {
//	private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private FileMapper fileMapper;
    
    @Autowired
	private ComService comService;
    
    private final String FILE_DATA_ROOT = "/ctc-work/HSMS/FILE_DATA";

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // 파일 단건 조회
    public FileModel selectFile(FileModel fileModel) {    	
    	
    	return fileMapper.selectFile(fileModel);
    }

	public void insertFile(FileModel fileModel) {
		// TODO Auto-generated method stub
		fileMapper.insertFile(fileModel);
	}

	public FileModel insertFile(File unzipDirFile,String gradeCode, String subjectCode, String ctgName, String qstFileName) {
		
		if(qstFileName == null) return new FileModel();
		
		String currentDate = dateUtils.getDateString("yyyy/MM/dd");
		String filePath = "/"+gradeCode+"/"+subjectCode+"/"+currentDate;
		
		FileModel fileModel = new FileModel();
		ComModel comModel = comService.selectUuid("FIL");
		fileModel.setFileId(comModel.getUuid());
		fileModel.setFileName(qstFileName);
		fileModel.setFilePath(filePath);
		
		File sourFile = new File(unzipDirFile,ctgName+"/"+qstFileName);
		
		if(!sourFile.exists()) {
			return new FileModel();
		}
		
		File renameSourceFile = new File(sourFile.getParentFile(),fileModel.getFileId());
		
		sourFile.renameTo(renameSourceFile);
		
		Path targetPath = Paths.get(FILE_DATA_ROOT+filePath);
		
		FileUtils.createDirectory(targetPath);
		
		FileUtils.copy(targetPath, renameSourceFile);
		
		this.insertFile(fileModel);
		return fileModel;
	}
	
public void copyFile(String qstFileId, Path tempPath) {
		
		if(!StringUtils.hasLength(qstFileId)) {
			return;
		}
		
		FileModel fileModel = new FileModel();
		fileModel.setFileId(qstFileId);
		FileModel retFileModel = this.selectFile(fileModel);

		String orgFilePathStr = retFileModel.getFilePath();
		String orgFileName = retFileModel.getFileName();
		Path filePath = Paths.get(FILE_DATA_ROOT + orgFilePathStr + "/" + qstFileId);
		try {
			Resource file = new UrlResource(filePath.toUri());
			FileUtils.save(tempPath, file);
			File copyFile = new File(tempPath.toFile(), qstFileId);
			copyFile.renameTo(new File(copyFile.getParent(), orgFileName));
		} catch (Exception e) {
			throw new RuntimeException("copyFile error");
		}

	}
}