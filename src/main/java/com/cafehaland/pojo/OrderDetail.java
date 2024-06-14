package com.cafehaland.pojo;


import com.cafehaland.pojo.id.OrderDetailId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
	@EmbeddedId
	private OrderDetailId id;
	
	@ManyToOne
	@MapsId("OrderId")
	@JoinColumn(name = "OrderId", referencedColumnName = "id")
	private Order order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("ProductSizeId")
	@JoinColumn(name = "ProductSizeId", referencedColumnName = "id")
	private ProductSize productSize;
	
	@Column
	private int quntity;

	public OrderDetail() {}

	public OrderDetailId getId() {
		return id;
	}

	public void setId(OrderDetailId id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}

	public int getQuntity() {
		return quntity;
	}

	public void setQuntity(int quntity) {
		this.quntity = quntity;
	}
	
	
}
