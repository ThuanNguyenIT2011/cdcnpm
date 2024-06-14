package com.cafehaland.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafehaland.Dao.StatisticProduct;
import com.cafehaland.exception.ProductNotFoundException;
import com.cafehaland.pojo.Product;
import com.cafehaland.users.service.ProductService;

@RestController
public class productRestController {
	@Autowired
	private ProductService service;
	
	@PostMapping("/products/check_name")
	public String checkName(@Param("id")Integer id, @Param("name")String name) {
		return service.isUniqueName(id, name) ? "Ok" : "Duplicated";
	}
	
	@GetMapping("/api/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id)  {
		try {
			Product product = service.getApi(id);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/products/statisticDay")
	public ResponseEntity<List<StatisticProduct>> getStatisticProductDay(){
		List<StatisticProduct> listStatisticProducts = service.getStatisticProductsDay();
		return new ResponseEntity<List<StatisticProduct>>(listStatisticProducts, HttpStatus.OK);
	} 
}
