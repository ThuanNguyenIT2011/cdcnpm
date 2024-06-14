package com.cafehaland.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.users.service.SupplierService;

@RestController
public class SupplierRestController {
	@Autowired
	private SupplierService service;
	
	@PostMapping("/Suppliers/check_name")
	public String checkName(@Param("id")Integer id, @Param("name") String name) {
		return service.isUniqueName(id, name) ? "Ok" : "Duplicated";
	}
}
