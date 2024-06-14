package com.cafehaland.pojo;

import java.beans.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "FirstName", length = 45, nullable = false)
	private String firstName;
	
	@Column(name = "LastName", length = 45, nullable = false)
	private String lastName;
	
	@Column(name = "Phone", length = 11)
	private String phone;
	
	@Column(length = 64)
	private String photos;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Email", unique = true, referencedColumnName = "email")
	private Account account;
	
	@OneToMany(mappedBy = "user")
	private List<Ingredient> ingredients;
	
	@OneToMany(mappedBy = "user")
	private List<Supplier> suppliers;
	
	@OneToMany(mappedBy = "user")
	private Set<Product> products = new HashSet<>();
	
	public User() {}
	
	public User(Integer id) { this.id = id; }
	
	public User(String firstName, String lastName, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Transient
	public String getPathPhotos() {
		if (this.id == null || this.photos == null || this.photos.isEmpty()) {
			return "/images/default-user.png";
		}
		return "/user-photo/" + this.id + "/" + this.photos;
	}
	
	@Transient
	public String getFullName() {
		return this.lastName + " " + this.firstName; 
	}
	
}
