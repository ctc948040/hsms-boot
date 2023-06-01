package com.hsms.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsms.mybatis.model.Basket;
import com.hsms.mybatis.model.Question;

/**
 * @author c.t.c
 * @date 2018/12/20
 */
@Mapper
public interface BasketMapper {
    
	List<Question> selectListBasket(Basket basket);

	void insertBasket(Basket basket);

	Question selectBasket(Basket basket);

	void insertListBasket(List<Basket> basketList);

	void deleteBasket(Basket basket);

	void deleteAllBasket(Basket basket);
}
