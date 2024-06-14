package com.cafehaland.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.pojo.Order;
import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.pojo.ProductSize;
import com.cafehaland.users.service.OrderDetailService;

@RestController
public class OrederDetailRestController {
	@Autowired
	private OrderDetailService service;
	
	@PostMapping("/orders/api/orderdetails")
	public ResponseEntity<HttpStatus> addListOrderDetails(@RequestBody List<OrderDetail> listOrderDetails){
		listOrderDetails.forEach(ele -> {
			ele.setOrder(new Order(ele.getId().getOrderId()));
			ele.setProductSize(new ProductSize(ele.getId().getProductSizeId()));
		});
		service.save(listOrderDetails);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
}
