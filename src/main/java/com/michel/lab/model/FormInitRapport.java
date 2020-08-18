package com.michel.lab.model;

import java.time.LocalDateTime;

public class FormInitRapport {
	
	private Integer qualification;
	private String titre;
	private String projet;
	private String demande;     	// référence de la demande d'essai
	private Integer auteur;
	private String date;
	private int version;		    // Edition du rapport
	private String identifiant;   	// référence du rapport, ex: R12-20
	private String objet;			// Contexte de la qualification: retour client, validation seconde source, ect.
	private String avis;
	
	public FormInitRapport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormInitRapport(Integer qualification, String titre, String projet, String demande, Integer auteur,
			String date, int version, String identifiant, String objet, String avis) {
		super();
		this.qualification = qualification;
		this.titre = titre;
		this.projet = projet;
		this.demande = demande;
		this.auteur = auteur;
		this.date = date;
		this.version = version;
		this.identifiant = identifiant;
		this.objet = objet;
		this.avis = avis;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getProjet() {
		return projet;
	}

	public void setProjet(String projet) {
		this.projet = projet;
	}

	public String getDemande() {
		return demande;
	}

	public void setDemande(String demande) {
		this.demande = demande;
	}

	public Integer getAuteur() {
		return auteur;
	}

	public void setAuteur(Integer auteur) {
		this.auteur = auteur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getAvis() {
		return avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	
	
}
