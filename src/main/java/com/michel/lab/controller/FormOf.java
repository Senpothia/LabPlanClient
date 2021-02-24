package com.michel.lab.controller;

import java.time.LocalDateTime;

public class FormOf {
	
	private Integer id;
	private Integer numero;
	private String produit;
	private String code;
	private String date;
	private Integer taille;
	private Integer createur;
	
	public FormOf() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public FormOf(Integer id, Integer numero, String produit, String code, String date, Integer taille,
			Integer createur) {
		super();
		this.id = id;
		this.numero = numero;
		this.produit = produit;
		this.code = code;
		this.date = date;
		this.taille = taille;
		this.createur = createur;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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

	

	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public Integer getCreateur() {
		return createur;
	}



	public void setCreateur(Integer createur) {
		this.createur = createur;
	}



	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}
	

}
