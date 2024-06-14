package com.cafehaland.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public Long countById(Integer id);
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ',u.firstName, ' ', u.lastName, ' ', u.account.email)"
			+ "LIKE %?1%")
	public Page<User> findAll(String keyWord, PageRequest pageable);
	
	
}
