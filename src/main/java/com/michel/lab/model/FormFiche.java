package com.michel.lab.model;

import java.time.LocalDateTime;

public class FormFiche {
	
	private Integer numero;
	private LocalDateTime date;
	private String statut;			// Close, ouverte
	private Integer niveau;			// gravité
	private String circonstance;
	private String observation;		// description du symptome, phénomène observé
	private String incidence;		// conséquences
	private String solution; 		// proposition
	private String domaine;			// électronique, mécanique
	private String objet;  			// n° de carte, pièce mécanique
	private Integer qualification;
	private Integer auteur;
	
	public FormFiche() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormFiche(Integer numero, LocalDateTime date, String statut, Integer niveau, String circonstance,
			String observation, String incidence, String solution, String domaine, String objet, Integer qualification,
			Integer auteur) {
		super();
		this.numero = numero;
		this.date = date;
		this.statut = statut;
		this.niveau = niveau;
		this.circonstance = circonstance;
		this.observation = observation;
		this.incidence = incidence;
		this.solution = solution;
		this.domaine = domaine;
		this.objet = objet;
		this.qualification = qualification;
		this.auteur = auteur;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Integer getNiveau() {
		return niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	public String getCirconstance() {
		return circonstance;
	}

	public void setCirconstance(String circonstance) {
		this.circonstance = circonstance;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getIncidence() {
		return incidence;
	}

	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public Integer getAuteur() {
		return auteur;
	}

	public void setAuteur(Integer auteur) {
		this.auteur = auteur;
	}

	
	
}
