package models.objects;

import java.util.Date;

public class InfoAdmin {
	
	private int orderId; // номер заказа
	private Date dateCreated;// дата создания заказа
	private String lastName; //фамилия
	private String firstName; // имя
	private String surName; // отчество	
	private String phoneUser; // номер телефона
	private String status; // статус заказа
	
	// Объект для вывода информации по заказам для администратора
	public InfoAdmin(int orderId, Date dateCreated, String lastName, String firstName, String surName,
			String phoneUser, String status) {		
		this.orderId = orderId;
		this.dateCreated = dateCreated;
		this.lastName = lastName;
		this.firstName = firstName;
		this.surName = surName;
		this.phoneUser = phoneUser;
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPhoneUser() {
		return phoneUser;
	}

	public void setPhoneUser(String phoneUser) {
		this.phoneUser = phoneUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	
	
}
