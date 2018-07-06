package com.aksh.marketlog.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ExecutionMessage {
	private float price;
	private int qty;
	private Date exectutionTime;
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	
	}

}
