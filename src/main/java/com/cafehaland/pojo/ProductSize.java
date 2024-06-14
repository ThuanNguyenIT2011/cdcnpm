package com.cafehaland.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
@Table(name="Product_Size")
public class ProductSize {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column()
	private double price;
	
	@Column(length = 3, nullable = false)
	private String size;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ProductId", nullable = false)
	@JsonBackReference
	private Product product;
	
	@OneToMany(mappedBy = "productSize", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Syntax> syntaxs = new ArrayList<>();
	
	public ProductSize() {}

	public ProductSize(Integer id) {
		this.id = id;
	}

	public ProductSize(Integer id, double price, String size, Product product) {
		this.id = id;
		this.price = price;
		this.size = size;
		this.product = product;
	}

	public ProductSize(String size, double price, Product product) {
		this.price = price;
		this.size = size;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<Syntax> getSyntaxs() {
		return syntaxs;
	}

	public void setSyntaxs(List<Syntax> syntaxs) {
		this.syntaxs = syntaxs;
	}
	
	public void addSyntax(Syntax syntax) {
		this.syntaxs.add(syntax);
	}
	
}
