package com.michel.lab.model;

import java.time.LocalDateTime;

public class FormEssai {
	
	private Integer numeo;
	private LocalDateTime date;
	private boolean resultat;
	private boolean statut;
	private Integer procedureId;
	private Integer technicienId;
	private Integer qualificationId;
	
	public FormEssai() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEssai(Integer numeo, LocalDateTime date, boolean resultat, boolean statut, Integer procedureId,
			Integer technicienId, Integer qualificationId) {
		super();
		this.numeo = numeo;
		this.date = date;
		this.resultat = resultat;
		this.statut = statut;
		this.procedureId = procedureId;
		this.technicienId = technicienId;
		this.qualificationId = qualificationId;
	}

	public Integer getNumeo() {
		return numeo;
	}

	public void setNumeo(Integer numeo) {
		this.numeo = numeo;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isResultat() {
		return resultat;
	}

	public void setResultat(boolean resultat) {
		this.resultat = resultat;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
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
	
	
}
