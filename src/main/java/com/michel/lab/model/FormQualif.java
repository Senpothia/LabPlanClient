package com.michel.lab.model;

import java.time.LocalDateTime;

public class FormQualif {
	
	private Integer numero;
	private String reference;
	private String produit;
	private String projet;
	private LocalDateTime date;
	private String objet;
	private String statut;  // Ouverte, cloturée
	private String resultat;
	private Integer createurId;
	
	
	public FormQualif() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FormQualif(Integer numero, String reference, String produit, String projet, LocalDateTime date, String objet,
			String statut, String resultat, Integer createurId) {
		super();
		this.numero = numero;
		this.reference = reference;
		this.produit = produit;
		this.projet = projet;
		this.date = date;
		this.objet = objet;
		this.statut = statut;
		this.resultat = resultat;
		this.createurId = createurId;
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


	public String getProjet() {
		return projet;
	}


	public void setProjet(String projet) {
		this.projet = projet;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public String getObjet() {
		return objet;
	}


	public void setObjet(String objet) {
		this.objet = objet;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getResultat() {
		return resultat;
	}


	public void setResultat(String resultat) {
		this.resultat = resultat;
	}


	public Integer getCreateurId() {
		return createurId;
	}


	public void setCreateurId(Integer createurId) {
		this.createurId = createurId;
	}


	
}
