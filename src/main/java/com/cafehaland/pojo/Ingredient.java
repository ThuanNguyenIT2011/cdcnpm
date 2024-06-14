package com.cafehaland.pojo;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ingredients")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 45, nullable = false, unique = true)
	private String name;
	
	@Column(length = 30, nullable = false)
	private String unit;
	
	@Column()
	private double stock;
	
	@Column(name = "Date")
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name="UserId")
	private User user;
	
//	@OneToMany(mappedBy = "ingredient")
//	private Set<ProductIngredient> productIngredients = new HashSet<>();
	
	public Ingredient() {}
	
	public Ingredient(Integer id) { this.id = id; }
	
	public Ingredient(String name, String unit, User user, double stock) {
		this.name = name;
		this.unit = unit;
		this.user = user;
		this.stock = stock;
	}
	
//	public Set<ProductIngredient> getProductIngredients() {
//		return productIngredients;
//	}

//	public void setProductIngredients(Set<ProductIngredient> productIngredients) {
//		this.productIngredients = productIngredients;
//	}

	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
