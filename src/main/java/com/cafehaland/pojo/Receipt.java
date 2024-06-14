package com.cafehaland.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Receipt")
public class Receipt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Date date;
	
	@Column(name = "Status", length = 20)
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SupplierId", nullable = false)
	private Supplier supplier;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt")
	private List<ReceiptDetail> receiptDetails = new ArrayList<>();
	

	public Receipt() {}
	
	public Receipt(Integer id) {
		this.id = id;
	}

	public Receipt(Date date, User user, Supplier supplier) {
		this.date = date;
		this.user = user;
		this.supplier = supplier;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public List<ReceiptDetail> getReceiptDetails() {
		return receiptDetails;
	}
	public void setReceiptDetails(List<ReceiptDetail> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isCanncel() {
		return this.status.equalsIgnoreCase("canncel");
	}
	
	public boolean isReceived() {
		return this.status.equalsIgnoreCase("received");
	}
	
	public boolean isOrdered() {
		return this.status.equalsIgnoreCase("ordered");
	}
	
	public boolean isOReceived() {
		return this.status.equalsIgnoreCase("Received");
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", date=" + date + ", status=" + status + ", user=" + user + ", supplier="
				+ supplier + ", receiptDetails=" + receiptDetails + "]";
	}
	
	
}
