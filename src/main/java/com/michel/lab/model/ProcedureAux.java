package com.michel.lab.model;

public class ProcedureAux {
	
	private Integer id;
	private String nom;   // nom de l'essai: ESD, Ondes de choc
	private String referentiel;  // EN 61000-4-5
	private String version;      // 2015, V1.8.1
	private String domaine;
	private boolean actif;
	
	public ProcedureAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcedureAux(Integer id, String nom, String referentiel, String version, String domaine, boolean actif) {
		super();
		this.id = id;
		this.nom = nom;
		this.referentiel = referentiel;
		this.version = version;
		this.domaine = domaine;
		this.actif = actif;
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

	public String getReferentiel() {
		return referentiel;
	}

	public void setReferentiel(String referentiel) {
		this.referentiel = referentiel;
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

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "ProcedureAux [id=" + id + ", nom=" + nom + ", referentiel=" + referentiel + ", version=" + version
				+ ", domaine=" + domaine + ", actif=" + actif + "]";
	}

	

}
