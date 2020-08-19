package com.michel.lab.model;

import java.util.List;

public class GroupeRapport {
	
	private Integer idRapport;
	private RapportAux rapport;
	private List<EssaiAux> essais;
	private List<EchantillonAux> echantillons;
	
	public GroupeRapport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GroupeRapport(Integer idRapport, RapportAux rapport, List<EssaiAux> essais,
			List<EchantillonAux> echantillons) {
		super();
		this.idRapport = idRapport;
		this.rapport = rapport;
		this.essais = essais;
		this.echantillons = echantillons;
	}

	public Integer getIdRapport() {
		return idRapport;
	}

	public void setIdRapport(Integer idRapport) {
		this.idRapport = idRapport;
	}

	public RapportAux getRapport() {
		return rapport;
	}

	public void setRapport(RapportAux rapport) {
		this.rapport = rapport;
	}

	public List<EssaiAux> getEssais() {
		return essais;
	}

	public void setEssais(List<EssaiAux> essais) {
		this.essais = essais;
	}

	public List<EchantillonAux> getEchantillons() {
		return echantillons;
	}

	public void setEchantillons(List<EchantillonAux> echantillons) {
		this.echantillons = echantillons;
	}
	
	
	

}
