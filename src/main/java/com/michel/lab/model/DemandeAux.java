package com.michel.lab.model;

import java.time.LocalDateTime;

public class DemandeAux {

	private Integer id;
	private String numero;
	private String date;
	private boolean statut; // Close, ouverte
	private String etat;
	private boolean attente;
	private String encours;
	private String produit;
	private String echantillon; // Description de l'échantillon
	private String origine; // Motivation de l'essai demandé
	private String essai; // travail attendu
	private String objectif;
	private String resultat;
	private String avis;
	private Integer idQualification;
	private String refQualification;
	private Integer idDemandeur;
	private String demandeur;
	private String technicien;
	private String rapport;
	private String auxiliaire;
	private String dateReponse;
	private String urgence;
	private String code;
	private String observation;

	public DemandeAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DemandeAux(Integer id, String numero, String date, boolean statut, String etat, boolean attente,
			String encours, String produit, String echantillon, String origine, String essai, String objectif,
			String resultat, String avis, Integer idQualification, String refQualification, Integer idDemandeur,
			String demandeur, String technicien, String rapport, String auxiliaire, String dateReponse, String urgence,
			String code, String observation) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.statut = statut;
		this.etat = etat;
		this.attente = attente;
		this.encours = encours;
		this.produit = produit;
		this.echantillon = echantillon;
		this.origine = origine;
		this.essai = essai;
		this.objectif = objectif;
		this.resultat = resultat;
		this.avis = avis;
		this.idQualification = idQualification;
		this.refQualification = refQualification;
		this.idDemandeur = idDemandeur;
		this.demandeur = demandeur;
		this.technicien = technicien;
		this.rapport = rapport;
		this.auxiliaire = auxiliaire;
		this.dateReponse = dateReponse;
		this.urgence = urgence;
		this.code = code;
		this.observation = observation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public String getEchantillon() {
		return echantillon;
	}

	public void setEchantillon(String echantillon) {
		this.echantillon = echantillon;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getEssai() {
		return essai;
	}

	public void setEssai(String essai) {
		this.essai = essai;
	}

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getAvis() {
		return avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	public Integer getIdQualification() {
		return idQualification;
	}

	public void setIdQualification(Integer idQualification) {
		this.idQualification = idQualification;
	}

	public String getRefQualification() {
		return refQualification;
	}

	public void setRefQualification(String refQualification) {
		this.refQualification = refQualification;
	}

	public Integer getIdDemandeur() {
		return idDemandeur;
	}

	public void setIdDemandeur(Integer idDemandeur) {
		this.idDemandeur = idDemandeur;
	}

	public String getDemandeur() {
		return demandeur;
	}

	public void setDemandeur(String demandeur) {
		this.demandeur = demandeur;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public boolean isAttente() {
		return attente;
	}

	public void setAttente(boolean attente) {
		this.attente = attente;
	}

	public String getEncours() {
		return encours;
	}

	public void setEncours(String encours) {
		this.encours = encours;
	}

	public String getTechnicien() {
		return technicien;
	}

	public void setTechnicien(String technicien) {
		this.technicien = technicien;
	}

	public String getRapport() {
		return rapport;
	}

	public void setRapport(String rapport) {
		this.rapport = rapport;
	}

	public String getAuxiliaire() {
		return auxiliaire;
	}

	public void setAuxiliaire(String auxiliaire) {
		this.auxiliaire = auxiliaire;
	}

	public String getDateReponse() {
		return dateReponse;
	}

	public void setDateReponse(String dateReponse) {
		this.dateReponse = dateReponse;
	}

	public String getUrgence() {
		return urgence;
	}

	public void setUrgence(String urgence) {
		this.urgence = urgence;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

}
