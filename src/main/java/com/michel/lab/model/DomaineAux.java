package com.michel.lab.model;

public class DomaineAux {
	
	private Integer id;
	private String nom;
	
	public DomaineAux() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public DomaineAux(Integer id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	

}
