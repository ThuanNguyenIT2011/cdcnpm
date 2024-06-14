package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Product;
import com.cafehaland.pojo.ProductSize;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer>{
	@Query("UPDATE ProductSize p SET p.price=?3 WHERE p.product=?1 AND p.size=?2")
	@Modifying
	public void updatePrice(Product product, String size, double price);
}
