package com.hsms.mybatis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hsms.mybatis.mapper.CategoryMapper;
import com.hsms.mybatis.model.Category;

/**
 * @author luoliang
 * @date 2018/12/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {
    private static final Logger log = LoggerFactory.getLogger(CategoryTest.class);
    @Resource
    private CategoryMapper categoryMapper;
    
    @Autowired
private MockMvc mvc;
    
    @Test
    public void TestGetcategory_thenStatus200()
      throws Exception {
    	
    	Category category = new Category();
    	category.setGradeCode("COMGRDM1");
    	category.setSubjectCode("COMSBJ01");

        log.info("{}", mvc.perform(get("/category")
        		.param("gradeCode", "COMGRDM1")
                .param("subjectCode", "COMSBJ01")
          .contentType(MediaType.APPLICATION_JSON)
        		).andReturn().getResponse().getContentAsString());
        
//        .getContentAsString(StandardCharsets.UTF_8)- 양호하지만 모든 응답에서 기본적으로 ISO 8859-1로 채워집니다.
//        SpringConfig 세팅
    }

    @Test
    public void testSelectListCategory() {
    	Category category = new Category();
    	category.setGradeCode("COMGRDM1");
    	category.setSubjectCode("COMSBJ01");
    	
    	log.info("{}",categoryMapper.selectListCategory(category));
    }
}
