package com.hsms.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hsms.mybatis.model.ComModel;

/**
 * @author c.t.c
 * @date 2023/06/04
 */
@Mapper
public interface ComMapper {
	ComModel selectUuid(ComModel comModel);
}
