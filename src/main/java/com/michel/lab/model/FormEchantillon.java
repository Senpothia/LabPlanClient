package com.michel.lab.model;

public class FormEchantillon {
	
	private Integer id;
	private Integer numero;
	private String caracteristique;
	private Integer version;
	private Integer qualification;   // numéro de qualification 
	private String date;
	
	public FormEchantillon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEchantillon(Integer id, Integer numero, String caracteristique, Integer version, Integer qualification,
			String date) {
		super();
		this.id = id;
		this.numero = numero;
		this.caracteristique = caracteristique;
		this.version = version;
		this.qualification = qualification;
		this.date = date;
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

	public String getCaracteristique() {
		return caracteristique;
	}

	public void setCaracteristique(String caracteristique) {
		this.caracteristique = caracteristique;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


}
