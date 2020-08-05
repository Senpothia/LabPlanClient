package com.michel.lab.model;

public class Groupe {
	
	private Integer domaine;   // id du domaine
	private Integer qualification;  // numéro de ma qualification
	
	public Groupe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Groupe(Integer domaine, Integer qualification) {
		super();
		this.domaine = domaine;
		this.qualification = qualification;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public void setDomaine(Integer domaine) {
		this.domaine = domaine;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}
	

}
