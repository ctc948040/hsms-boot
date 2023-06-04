package com.hsms.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsms.mybatis.model.Category;

/**
 * @author luoliang
 * @date 2018/12/20
 */
@Mapper
public interface CategoryMapper {
    
    List<Category> selectListCategory(Category category);

	List<Category> selectListCategoryAll(Category category);

	int insertCategory(Category category);
	int updateCategory(Category category);
}
