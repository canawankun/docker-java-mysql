/**
 * 
 */
package com.aksh.marketlog.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author arawa3
 *
 */
public class NewOrder {

	private int id;
	private int refId;
	private OrderType type;
	private String stock;
	private float price;
	private int qty;
	private int remainingQty;
	private OrderStatus status;
	private Date entryTime;
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
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
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
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getRemainingQty() {
		return remainingQty;
	}
	public void setRemainingQty(int remainingQty) {
		this.remainingQty = remainingQty;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	
	}
	
	
	
}
