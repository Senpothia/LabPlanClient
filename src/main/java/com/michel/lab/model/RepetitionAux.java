package com.michel.lab.model;

public class RepetitionAux {
	
	Integer id;
	Integer anomalie;
	Integer of;
	String produit;
	Integer total;
	
	public RepetitionAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepetitionAux(Integer id, Integer anomalie, Integer of, String produit, Integer total) {
		super();
		this.id = id;
		this.anomalie = anomalie;
		this.of = of;
		this.produit = produit;
		this.total = total;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnomalie() {
		return anomalie;
	}

	public void setAnomalie(Integer anomalie) {
		this.anomalie = anomalie;
	}

	public Integer getOf() {
		return of;
	}

	public void setOf(Integer of) {
		this.of = of;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
	
	

}
