package com.cafehaland.users.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	@Query("SELECT s FROM Supplier s WHERE CONCAT(s.name, ' ', s.email, ' ', s.phone, s.id) LIKE %?1%")
	public List<Supplier> findAll(String keyword, Sort sort);
	
	@Query("SELECT s FROM Supplier s WHERE s.name = ?1")
	public Supplier findSupplierByName(String name);
}
