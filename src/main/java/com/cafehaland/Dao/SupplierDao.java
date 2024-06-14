package com.cafehaland.Dao;

import java.util.HashSet;
import java.util.Set;

import com.cafehaland.pojo.Ingredient;

public class SupplierDao {
	private Integer id;
	private String name;
	private String email;
	private String phone;
	private Set<Ingredient> ingredients = new HashSet<>();
	
	public SupplierDao(Integer id, String name, String email, String phone, Set<Ingredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.ingredients = ingredients;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
	
}
