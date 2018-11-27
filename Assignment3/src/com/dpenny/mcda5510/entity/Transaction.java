package com.dpenny.mcda5510.entity;

public class Transaction {
	private int id;
	private String nameOnCard;
	private String cardNumber;
	private double unitPrice;
	private int quantity;
	private double totalPrice;
	private String expDate;
	private String createdOn;
	private String createdBy = System.getProperty("user.name");
	private String cardType;

	public void setId(int value) {
		this.id = value;

	}

	public void setNameOnCard(String value) {
		this.nameOnCard = value;

	}

	public void setCardNumber(String value) {
		this.cardNumber = value;

	}

	public void setUnitPrice(double value) {
		this.unitPrice = value;

	}

	public void setQuantity(int value) {
		this.quantity = value;

	}

	public void setTotalPrice(double value) {
		this.totalPrice = value;
	}

	public void setExpDate(String value) {
		this.expDate = value;
	}

	public void setCreatedOn(String value) {
		this.createdOn = value;
	}

	public void setCreatedBy(String value) {
		this.createdBy = value;
	}

	public int getId() {
		return id;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}