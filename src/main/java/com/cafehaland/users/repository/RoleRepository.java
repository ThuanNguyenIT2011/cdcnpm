package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafehaland.pojo.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
