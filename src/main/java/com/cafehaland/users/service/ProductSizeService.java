package com.cafehaland.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafehaland.pojo.ProductSize;
import com.cafehaland.pojo.Syntax;
import com.cafehaland.users.repository.ProductSizeRepository;

@Service
public class ProductSizeService {
	@Autowired
	private ProductSizeRepository productSizeRepo;
	
	public boolean checkInStock(Integer id, double quantity) {
		ProductSize productSize = productSizeRepo.findById(id).get();
		for(Syntax syn : productSize.getSyntaxs()) {
			if (syn.getQuantity() * quantity > syn.getIngredient().getStock()) {
				return false;
			}
		}
		return true;
	}
}
