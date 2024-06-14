package com.cafehaland.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.users.service.IngredientService;


@RestController
public class IngredientRestController {
	@Autowired
	private IngredientService service;
	
	@PostMapping("/ingredients/check_name")
	public String checkName(@Param("id")Integer id, @Param("name")String name) {
		return service.isUniqueName(id, name) ? "Ok" : "Duplicated";
	}
	
	@GetMapping("/ingredints/check_quantity")
	public String checkStock() {
		return service.checkQuantityStock() ? "" : "One ingredient is almost all gone";
	}
	
}
