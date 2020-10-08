package com.michel.lab.controller;

import java.time.LocalDateTime;

public class FormIncident {
	
	private String date;
	private String produit;
	private String code;
	private String description;
	
	public FormIncident() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FormIncident(String date, String produit, String code, String description) {
		super();
		this.date = date;
		this.produit = produit;
		this.code = code;
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
