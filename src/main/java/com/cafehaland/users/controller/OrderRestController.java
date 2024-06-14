package com.cafehaland.users.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.Security.AccountDetail;
import com.cafehaland.pojo.Order;
import com.cafehaland.pojo.User;
import com.cafehaland.users.service.OrderService;

@RestController
public class OrderRestController {
	@Autowired
	private OrderService service;
	
	@PostMapping("/orders/api/add")
	public int createOrder(@RequestBody Order order, @AuthenticationPrincipal AccountDetail logged){
		order.setUser(new User(logged.getIduser()));
		order.setDate(new Date());
		Order saveOrder = service.save(order);
		//return new ResponseEntity<>(HttpStatus.CREATED);
		return saveOrder.getId();
	}
}
