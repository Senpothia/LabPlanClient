package com.michel.lab.model;

import java.time.LocalDateTime;

public class NoteAux {

	private Integer id;
	private Integer numero;
	private String date;
	private String texte;
	private String auteur;

	private boolean active;

	public NoteAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoteAux(Integer id, Integer numero, String date, String texte, String auteur, boolean active) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.texte = texte;
		this.auteur = auteur;
		this.active = active;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
