package com.cafehaland.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = true)
	private String name;
	
	@Column()
	private boolean enabled;
	
	@Column(length = 255)
	private String description;
	
	@Column(length = 65, nullable = false)
	private String image;
	
	@Column
	private Date date;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProductSize> productSizes = new ArrayList<>();
	
	@ManyToOne()
	@JoinColumn(name="UserId")
	private User user;

	public Product() {}
	
	
	public Product(Integer id) { this.id = id; }
	
	public Product(String name, boolean enabled, User user) {
		this.name = name;
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ProductSize> getProductSizes() {
		return productSizes;
	}

	public void setProductSizes(List<ProductSize> productSizes) {
		this.productSizes = productSizes;
	}
	
	public void addSize(ProductSize size) {
		this.productSizes.add(size);
	}
	
	public void addSize(String name, double price) {
		this.productSizes.add(new ProductSize(name, price, this));
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", enabled=" + enabled + ", description=" + description
				+ ", image=" + image + ", date=" + date + ", productSizes=" + productSizes + ", user=" + user + "]";
	}
	
	
	@Transient
	public String getPathImage() {
		if (this.id == null)
			return "/images/default-product.png";
		return "/product-photo/" + this.id + "/" + this.getImage();
	}
	
}
