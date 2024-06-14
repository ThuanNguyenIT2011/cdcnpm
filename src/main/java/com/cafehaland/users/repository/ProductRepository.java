package com.cafehaland.users.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Product;
import com.cafehaland.pojo.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("UPDATE Product p set p.enabled=?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabled(Integer id, boolean enabled);
	
	public Long countById(Integer id);
	
	@Query("UPDATE Product p SET p.name=?1, p.description=?2, p.image=?3, p.enabled=?4,"
			+ "p.date=?5, p.user=?6 WHERE p.id=?7")
	@Modifying
	public void update(String name, String des, String image, boolean status,
			Date date, User user, Integer id);
	
	@Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.id, ' ', p.description)"
			+ "LIKE %?1%")
	public Page<Product> findAll(String keyword, PageRequest pageRequest);
	
	@Query("SELECT p FROM Product p WHERE p.name = ?1")
	public Product findProductByName(String name);
	
	@Query(value = "SELECT DISTINCT sy.ingredient_id FROM ("
			+ "	SELECT id FROM products WHERE id = ?1 "
			+ "	) AS p "
			+ "	INNER JOIN (SELECT id AS size_id, product_id FROM product_size) AS s "
			+ "	ON p.id = s.product_id "
			+ "	INNER JOIN (SELECT ingredient_id, product_size_id FROM syntaxs) AS sy "
			+ "	ON sy.product_size_id = s.size_id", nativeQuery = true)
	public List<Integer> listIngredientByProduct(Integer id);
}
