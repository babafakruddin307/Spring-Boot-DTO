package com.jpa.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="proddemo")
public class Product {
	@Id
	private Integer prodId;
	private String prdName;
	private String prodDesc;
	private Double prodCost;
	
	public Product() {
	}
	public Product(Integer prodId, String prdName, String prodDesc, Double prodCost) {
		super();
		this.prodId = prodId;
		this.prdName = prdName;
		this.prodDesc = prodDesc;
		this.prodCost = prodCost;
	}

	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public Double getProdCost() {
		return prodCost;
	}
	public void setProdCost(Double prodCost) {
		this.prodCost = prodCost;
	}
	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prdName=" + prdName + ", prodDesc=" + prodDesc + ", prodCost="
				+ prodCost + "]";
	}
	
	

}
