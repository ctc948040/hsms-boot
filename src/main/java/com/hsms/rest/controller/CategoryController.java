package com.hsms.rest.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsms.mybatis.model.Category;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.CategoryService;
import com.hsms.util.ExcelGenerator;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 학년,과목에 해당하는 카테고리중에 2레벨까지만
     * @param category
     * @return
     */
    @GetMapping("/selectListCategory")
    public ResponseEntity<DefaultRes<List<Category>>> selectListCategory(Category category) {
        return new ResponseEntity<DefaultRes<List<Category>>>(categoryService.selectListCategory(category), HttpStatus.OK);
    }
    /**
     * 학년,과목에 해당하는 모든 카테고리를 계층별로 가져옴
     * @param category
     * @return
     */
    @GetMapping("/selectListCategoryAll")
    public ResponseEntity<DefaultRes<List<Category>>> selectListCategoryAll(Category category) {
    	return new ResponseEntity<DefaultRes<List<Category>>>(categoryService.selectListCategoryAll(category), HttpStatus.OK);
    }
    
    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(Category category,HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=category" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        DefaultRes<List<Category>> dr = categoryService.selectListCategoryAll(category);
        List<Category> list = dr.getData();
        
        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
		List<Map<String,String>> listMap = objectMapper.convertValue(list, List.class);
                
        List<String> keyList = Arrays.asList(new String[]{"gradeCode","gradeName","subjectCode","subjectName","parentCtgId","parentCtgName","ctgId","ctgName","ctgLevel","ctgSort"});
        List<String> displayList = Arrays.asList(new String[]{"학년코드[gradeCode]","학년[gradeName]","과목코드[subjectCode]","과목명[subjectName]","부모카테고리아이디[parentCtgId]","부모카테고리명[parentCtgName]","카테고리아이디[ctgId]","카테고리명[ctgName]","레벨[ctgLevel]","순번[ctgSort]"});

        ExcelGenerator generator = new ExcelGenerator(keyList,displayList,listMap);
        generator.generateExcelFile(response);
    }
}
