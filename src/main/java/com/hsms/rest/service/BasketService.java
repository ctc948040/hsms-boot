package com.hsms.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.BasketMapper;
import com.hsms.mybatis.model.Basket;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;

@Service
public class BasketService {
//	private static final Logger log = LoggerFactory.getLogger(BasketService.class);
    private BasketMapper basketMapper;

    public BasketService(BasketMapper basketMapper) {
        this.basketMapper = basketMapper;
    }

    // 카테고리 조회
    public DefaultRes<List<Question>> selectListBasket(Basket basket) {
    	
    	
        List<Question> questionList = basketMapper.selectListBasket(basket);
        
        if (questionList.isEmpty()) {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_BASKET);
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_BASKET, questionList);
    }

	public DefaultRes<Question> insertBasket(Basket basket) {
		basketMapper.insertBasket(basket);
		Question question = basketMapper.selectBasket(basket);
		return DefaultRes.res(StatusCode.OK, ResponseMessage.INSERT_BASKET, question);
	}

	public DefaultRes<Question> insertListBasket(List<Basket> basketList) {
		
		for(Basket basket : basketList) {
			basketMapper.insertBasket(basket);
		}
		
		Question question = basketMapper.selectBasket(basketList.get(0));
		return DefaultRes.res(StatusCode.OK, ResponseMessage.INSERT_BASKET, question);
	}
}
