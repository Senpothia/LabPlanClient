package com.michel.lab.model;

import java.time.LocalDateTime;

public class FormEssai {
	
	private Integer id;
	private Integer numero;
	private LocalDateTime date;
	private String nom;
	private String resultat;
	private String statut;
	private String produit;
	private Integer procedureId;
	private Integer technicienId;
	private Integer qualificationId;
	private String version;
	private String domaine;
	
	public FormEssai() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEssai(Integer id, Integer numero, LocalDateTime date, String nom, String resultat, String statut,
			String produit, Integer procedureId, Integer technicienId, Integer qualificationId, String version,
			String domaine) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.nom = nom;
		this.resultat = resultat;
		this.statut = statut;
		this.produit = produit;
		this.procedureId = procedureId;
		this.technicienId = technicienId;
		this.qualificationId = qualificationId;
		this.version = version;
		this.domaine = domaine;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public Integer getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(Integer procedureId) {
		this.procedureId = procedureId;
	}

	public Integer getTechnicienId() {
		return technicienId;
	}

	public void setTechnicienId(Integer technicienId) {
		this.technicienId = technicienId;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	

}
