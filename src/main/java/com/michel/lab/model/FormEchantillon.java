package com.michel.lab.model;

public class FormEchantillon {
	
	private Integer numero;
	private String caracteristique;
	private Integer version;
	
	public FormEchantillon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEchantillon(Integer numero, String caracteristique, Integer version) {
		super();
		this.numero = numero;
		this.caracteristique = caracteristique;
		this.version = version;
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

	
}
