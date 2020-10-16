package com.michel.lab.controller;

public class FormIncident {
	
	private Integer id;
	private Integer commercial;
	private String date;
	private String produit;
	private String code;
	private String description;
	private Integer recurrence;
	
	public FormIncident() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public FormIncident(Integer id, Integer commercial, String date, String produit, String code, String description,
			Integer recurrence) {
		super();
		this.id = id;
		this.commercial = commercial;
		this.date = date;
		this.produit = produit;
		this.code = code;
		this.description = description;
		this.recurrence = recurrence;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
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


	public Integer getRecurrence() {
		return recurrence;
	}


	public void setRecurrence(Integer recurrence) {
		this.recurrence = recurrence;
	}
	

}
