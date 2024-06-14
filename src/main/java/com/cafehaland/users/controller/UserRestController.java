package com.cafehaland.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.users.service.UserService;

@RestController
public class UserRestController {
	@Autowired
	private UserService service;
	@GetMapping("/staff/getLengthStaff")
	public int getLengthUser() {
		return service.getLengthStaff();
	}
}
