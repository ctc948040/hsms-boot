package com.hsms.rest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.Basket;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.BasketService;

@RestController
@RequestMapping("/basket")
public class BasketController {
//	private static final Logger log = LoggerFactory.getLogger(BasketController.class);
	 private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }
	
	@GetMapping("/selectListBasket")
	public ResponseEntity<DefaultRes<List<Question>>> selectListBasket(Basket basket) {
		
		return new ResponseEntity<DefaultRes<List<Question>>>(basketService.selectListBasket(basket), HttpStatus.OK);
	}
	
	@PostMapping("/insertBasket")
	public ResponseEntity<DefaultRes<Question>> insertBasket(Basket basket) {
		
		return new ResponseEntity<DefaultRes<Question>>(basketService.insertBasket(basket), HttpStatus.OK);
	}
}