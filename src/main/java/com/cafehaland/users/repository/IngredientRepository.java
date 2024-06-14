package com.cafehaland.users.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
	@Query("SELECT i FROM Ingredient i WHERE CONCAT(i.name, ' ', i.id, ' ', i.unit) LIKE %?1%")
	public List<Ingredient> findAll(String keyword, Sort sort);
	
	@Query("SELECT i FROM Ingredient i WHERE i.name = ?1")
	public Ingredient findIngredientByName(String name);
	
	@Query("UPDATE Ingredient i SET i.stock = (i.stock + ?2) WHERE i.id=?1")
	@Modifying
	public void updateStock(Integer id, double quantity);
	
	@Query("UPDATE Ingredient i SET i.stock = (i.stock - ?2) WHERE i.id=?1")
	@Modifying
	public void updateStockMinus(Integer id, double quantity);
	
}
