package com.hsms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.Category;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/selectListCategory")
    public ResponseEntity<DefaultRes<List<Category>>> selectListCategory(Category category) {
        return new ResponseEntity<DefaultRes<List<Category>>>(categoryService.selectListCategory(category), HttpStatus.OK);
    }
}
