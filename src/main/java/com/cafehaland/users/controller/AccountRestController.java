package com.cafehaland.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.users.service.AccountService;

@RestController
public class AccountRestController {
	@Autowired
	private AccountService service;
	
	@PostMapping("/staffs/check_email")
	public String checkDuplicateEmail(@Param("idUser") Integer idUser, @Param("email") String email) {
		System.out.println("ID: " + idUser);
		return service.isUniqueEmail(idUser, email) ? "OK" : "Duplicated";
	}
	
}
