package com.michel.lab.model;

public class FormNote {

	private Integer id;
	private Integer auteur; // identifiant de l'auteur
	private Integer qualification; // numéro de la qualification
	private String Date;
	private String texte;

	public FormNote() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormNote(Integer id, Integer auteur, Integer qualification, String date, String texte) {
		super();
		this.id = id;
		this.auteur = auteur;
		this.qualification = qualification;
		Date = date;
		this.texte = texte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuteur() {
		return auteur;
	}

	public void setAuteur(Integer auteur) {
		this.auteur = auteur;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

}
