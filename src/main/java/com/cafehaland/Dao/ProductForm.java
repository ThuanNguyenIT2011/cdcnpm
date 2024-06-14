package com.cafehaland.Dao;

import java.util.Map;
import java.util.TreeMap;

import com.cafehaland.pojo.Product;
import com.cafehaland.pojo.ProductSize;

public class ProductForm {
	private Integer id;
	private String name;
	private boolean enabled;
	private String description;
	private String image;
	
	Map<String, Double> sizes;

	public ProductForm() {
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

	public Map<String, Double> getSizes() {
		return sizes;
	}

	public void setSizes(Map<String, Double> sizes) {
		this.sizes = sizes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void addSize(String nameSize, double price) {
		this.sizes.put(nameSize, price);
	}
	
	public static ProductForm changeProductToProductForm(Product product) {
		ProductForm form = new ProductForm();
		form.setId(product.getId());
		form.setName(product.getName());
		form.setDescription(product.getDescription());
		form.setEnabled(product.isEnabled());
		form.setImage(product.getImage());
		
		form.setSizes(new TreeMap<>());
		for (ProductSize size : product.getProductSizes()) {
			form.addSize(size.getSize(), size.getPrice());
		}
		
		return form;
	}
	
}
