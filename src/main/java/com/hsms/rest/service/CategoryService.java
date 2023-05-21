package com.hsms.rest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.CategoryMapper;
import com.hsms.mybatis.model.Category;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;

@Service("categoryService")
public class CategoryService {
	private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 카테고리 조회
    public DefaultRes<List<Category>> selectListCategory(Category category) {
        final List<Category> categoryList = categoryMapper.selectListCategory(category);
        if (categoryList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CATEGORY);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CATEGORY, categoryList);
    }
}