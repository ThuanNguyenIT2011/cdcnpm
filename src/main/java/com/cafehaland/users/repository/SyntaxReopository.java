package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.ProductSize;
import com.cafehaland.pojo.Syntax;
import com.cafehaland.pojo.id.SyntaxId;

@Repository
public interface SyntaxReopository extends JpaRepository<Syntax, SyntaxId>{
	public Long countById(SyntaxId id);
	
	@Query("DELETE Syntax s WHERE s.ingredient = ?2 AND s.productSize = ?1")
	@Modifying
	public void deleteByProductSizeAndIngredient(ProductSize productSize, Ingredient ingredient);
}
