package com.hsms.rest.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.Basket;
import com.hsms.mybatis.model.Question;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.BasketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/basket")
public class BasketController {
	private static final Logger log = LoggerFactory.getLogger(BasketController.class);
	 private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }
	
    @PostMapping("/selectListBasket")
	public ResponseEntity<DefaultRes<List<Question>>> selectListBasket(@RequestBody Basket basket) {
		
		return new ResponseEntity<DefaultRes<List<Question>>>(basketService.selectListBasket(basket), HttpStatus.OK);
	}
    
    @PostMapping("/deleteBasket")
	public ResponseEntity<DefaultRes<?>> deleteBasket(@RequestBody Basket basket) {
		
		return new ResponseEntity<DefaultRes<?>>(basketService.deleteBasket(basket), HttpStatus.OK);
	}
    @PostMapping("/deleteAllBasket")
    public ResponseEntity<DefaultRes<?>> deleteAllBasket(@RequestBody Basket basket) {
    	
    	return new ResponseEntity<DefaultRes<?>>(basketService.deleteAllBasket(basket), HttpStatus.OK);
    }
	
	@PostMapping("/insertBasket")
	public ResponseEntity<DefaultRes<Question>> insertBasket(@RequestBody Basket basket) {
		return new ResponseEntity<DefaultRes<Question>>(basketService.insertBasket(basket), HttpStatus.OK);
	}
	@PostMapping("/insertListBasket")
	public ResponseEntity insertListBasket(@RequestBody List<Basket> basketList) {
		return new ResponseEntity<DefaultRes<Question>>(basketService.insertListBasket(basketList), HttpStatus.OK);
	}
}