package com.cafehaland.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.Dao.SupplierDao;
import com.cafehaland.exception.SupplierNotFoundException;
import com.cafehaland.pojo.Supplier;
import com.cafehaland.users.service.SupplierService;

@RestController
public class ReceiptRestController {
	@Autowired
	private SupplierService service;
	@GetMapping("/suppliers/{id}/list_ingredient")
	public SupplierDao findSupplierById(@PathVariable("id") Integer id) throws SupplierNotFoundException {
		try {
			Supplier supplier = service.get(id);
			return new SupplierDao(supplier.getId(), supplier.getName(), supplier.getEmail(), 
					supplier.getPhone(), supplier.getIngredients());
		} catch (SupplierNotFoundException e) {
			throw new SupplierNotFoundException("Not Found");
		}
	}
}
