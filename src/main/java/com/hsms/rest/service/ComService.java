package com.hsms.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hsms.mybatis.mapper.ComMapper;
import com.hsms.mybatis.model.ComModel;

@Service
public class ComService {
//	private static final Logger log = LoggerFactory.getLogger(ComService.class);
    private ComMapper comMapper;

    public ComService(ComMapper comMapper) {
        this.comMapper = comMapper;
    }

    // uuid 값 구하기
    public ComModel selectUuid(String prefix) {
    	if(!StringUtils.hasLength(prefix)) {
    		prefix = "DIR";
    	}
    	ComModel comModel= new ComModel();
    	comModel.setPrefix(prefix);
    	return comMapper.selectUuid(comModel);
    }
    // uuid 값 구하기
    public ComModel selectUuid() {
    	ComModel comModel= new ComModel();
    	comModel.setPrefix("DIR");
    	return comMapper.selectUuid(comModel);
    }
}