package com.michel.lab.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QualificationAux {
	
	private Integer numero;
	private String reference;
	private String produit;
	private String date;
	private String objet;
	private String projet;
	private String createur;
	private String resultat;
	private String statut;
	//private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public QualificationAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QualificationAux(Integer numero, String reference, String produit, String date, String objet,
			String projet, String createur, String resultat, String statut) {
		super();
		this.numero = numero;
		this.reference = reference;
		this.produit = produit;
		this.date = date;
		this.objet = objet;
		this.projet = projet;
		this.createur = createur;
		this.resultat = resultat;
		this.statut = statut;
	}
	
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getProjet() {
		return projet;
	}

	public void setProjet(String projet) {
		this.projet = projet;
	}

	public String getCreateur() {
		return createur;
	}

	public void setCreateur(String createur) {
		this.createur = createur;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}



	
	
}
