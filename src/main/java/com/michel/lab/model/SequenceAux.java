package com.michel.lab.model;

import java.time.LocalDateTime;

public class SequenceAux {
	
	private Integer id;
	private String commentaire;
	private LocalDateTime debut;
	private LocalDateTime fin;
	private String niveau;
	private String nom;
	private Integer numero;  // numéro de la séquence
	private String profil;
	private Integer essai;
	private String nomEssais;
	private Integer domaine;
	private String nomDomaine;
	private boolean statut;
	private String actif;
	private Integer qualification;  // numéro de la qualification
	private boolean resultat;
	private String avis;            // ex: conforme, non conforme
	
	public SequenceAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SequenceAux(Integer id, String commentaire, LocalDateTime debut, LocalDateTime fin, String niveau,
			String nom, Integer numero, String profil, Integer essai, String nomEssais, Integer domaine,
			String nomDomaine, boolean statut, String actif, Integer qualification, boolean resultat, String avis) {
		super();
		this.id = id;
		this.commentaire = commentaire;
		this.debut = debut;
		this.fin = fin;
		this.niveau = niveau;
		this.nom = nom;
		this.numero = numero;
		this.profil = profil;
		this.essai = essai;
		this.nomEssais = nomEssais;
		this.domaine = domaine;
		this.nomDomaine = nomDomaine;
		this.statut = statut;
		this.actif = actif;
		this.qualification = qualification;
		this.resultat = resultat;
		this.avis = avis;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public LocalDateTime getDebut() {
		return debut;
	}

	public void setDebut(LocalDateTime debut) {
		this.debut = debut;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public Integer getEssai() {
		return essai;
	}

	public void setEssai(Integer essai) {
		this.essai = essai;
	}

	public String getNomEssais() {
		return nomEssais;
	}

	public void setNomEssais(String nomEssais) {
		this.nomEssais = nomEssais;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public void setDomaine(Integer domaine) {
		this.domaine = domaine;
	}

	public String getNomDomaine() {
		return nomDomaine;
	}

	public void setNomDomaine(String nomDomaine) {
		this.nomDomaine = nomDomaine;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public String getActif() {
		return actif;
	}

	public void setActif(String actif) {
		this.actif = actif;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public boolean isResultat() {
		return resultat;
	}

	public void setResultat(boolean resultat) {
		this.resultat = resultat;
	}

	public String getAvis() {
		return avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	
}
