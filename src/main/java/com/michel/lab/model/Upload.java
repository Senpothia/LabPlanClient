package com.michel.lab.model;

public class Upload {
	
	private Integer qualification;
	private String image;
	private String avis;

	public Upload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Upload(Integer qualification, String image, String avis) {
		super();
		this.qualification = qualification;
		this.image = image;
		this.avis = avis;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAvis() {
		return avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	

}
