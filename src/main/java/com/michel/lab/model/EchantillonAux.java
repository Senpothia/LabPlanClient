package com.michel.lab.model;

import java.time.LocalDateTime;

public class EchantillonAux {
	
	private Integer id;
	private Integer numero;
	private LocalDateTime date;
	private Integer version;
	private String caracteristique;    // Variantes pouvant distinguer les échantillons
	private String actif;
	private boolean statut;
	private Integer qualification;
	
	public EchantillonAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EchantillonAux(Integer id, Integer numero, LocalDateTime date, Integer version, String caracteristique,
			String actif, boolean statut, Integer qualification) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.version = version;
		this.caracteristique = caracteristique;
		this.actif = actif;
		this.statut = statut;
		this.qualification = qualification;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCaracteristique() {
		return caracteristique;
	}

	public void setCaracteristique(String caracteristique) {
		this.caracteristique = caracteristique;
	}

	public String getActif() {
		return actif;
	}

	public void setActif(String actif) {
		this.actif = actif;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	

}
