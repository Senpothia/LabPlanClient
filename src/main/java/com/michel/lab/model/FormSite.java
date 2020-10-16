package com.michel.lab.model;

public class FormSite {
	

	private Integer id;
	private String nom;
	private Integer numero;
	private String voie;
	private String ville;
	private Integer codePostal;
	private String secteur;
	private Integer departement;
	private String region;
	private Integer commercial;
	
	public FormSite() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public FormSite(Integer id, String nom, Integer numero, String voie, String ville, Integer codePostal,
			String secteur, Integer departement, String region, Integer commercial) {
		super();
		this.id = id;
		this.nom = nom;
		this.numero = numero;
		this.voie = voie;
		this.ville = ville;
		this.codePostal = codePostal;
		this.secteur = secteur;
		this.departement = departement;
		this.region = region;
		this.commercial = commercial;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getVoie() {
		return voie;
	}

	public void setVoie(String voie) {
		this.voie = voie;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public Integer getDepartement() {
		return departement;
	}

	public void setDepartement(Integer departement) {
		this.departement = departement;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}



	public Integer getCommercial() {
		return commercial;
	}



	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}
	
	

}
