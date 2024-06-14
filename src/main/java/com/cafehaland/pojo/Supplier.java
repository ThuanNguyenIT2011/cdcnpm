package com.cafehaland.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Suppliers")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(length = 45, name = "Name",nullable = false, unique = true)
	private String name;
	
	@Column(length = 128, name = "Email", nullable = false)
	private String email;
	
	@Column(length = 12, name = "Phone")
	private String phone;
	
	@Column(name = "Date")
	private Date date;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "Suppliers_Ingredients", 
			joinColumns = @JoinColumn(name = "SupplierId"),
			inverseJoinColumns = @JoinColumn(name = "IngredientId"))
	private Set<Ingredient> ingredients = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="UserId")
	private User user;
	
	public Supplier() {}
	
	public Supplier(Integer id) { this.id = id; }
	
	public Supplier(String name, String email, String phone, User user) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
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
