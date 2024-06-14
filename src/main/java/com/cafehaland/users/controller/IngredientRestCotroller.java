package com.cafehaland.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.pojo.Ingredient;
import com.cafehaland.users.service.IngredientService;

@RestController
@CrossOrigin(origins = "*")
public class IngredientRestCotroller {
	@Autowired
	private IngredientService service;
	
	@GetMapping("/ingredients/getlistingredient")
	public ResponseEntity<List<Ingredient>> getListIngredient(){
		return new ResponseEntity<List<Ingredient>>(service.listAll(), HttpStatus.OK);
	}
}
