package com.michel.lab.model;

public class RecurrenceAux {
	
	private Integer site;
	private Integer defaut;
	private Integer nombre;
	
	public RecurrenceAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecurrenceAux(Integer site, Integer defaut, Integer nombre) {
		super();
		this.site = site;
		this.defaut = defaut;
		this.nombre = nombre;
	}

	public Integer getSite() {
		return site;
	}

	public void setSite(Integer site) {
		this.site = site;
	}

	public Integer getDefaut() {
		return defaut;
	}

	public void setDefaut(Integer defaut) {
		this.defaut = defaut;
	}

	public Integer getNombre() {
		return nombre;
	}

	public void setNombre(Integer nombre) {
		this.nombre = nombre;
	}
	
	
}
