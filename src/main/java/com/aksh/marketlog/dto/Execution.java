package com.aksh.marketlog.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Execution {
	private int id;
	private int refId;
	private String stock;
	private float price;
	private int qty;
	private Date exectutionTime;
	private Date lastUpdate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Date getExectutionTime() {
		return exectutionTime;
	}
	public void setExectutionTime(Date exectutionTime) {
		this.exectutionTime = exectutionTime;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	
	}

}
