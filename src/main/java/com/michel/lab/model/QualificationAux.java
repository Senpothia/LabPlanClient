package com.michel.lab.model;

public class QualificationAux {
	
	private String numero;
	private String reference;
	private String produit;
	private String date;
	private String objet;
	private String projet;
	private String createur;
	private boolean resultat;
	private boolean statut;
	
	public QualificationAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QualificationAux(String numero, String reference, String produit, String date, String objet, String projet,
			String createur, boolean resultat, boolean statut) {
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public boolean isResultat() {
		return resultat;
	}

	public void setResultat(boolean resultat) {
		this.resultat = resultat;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}
	
	
}
