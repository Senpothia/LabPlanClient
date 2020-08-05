package com.michel.lab.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private")
public class Private {
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	@Autowired
	private UserConnexion userConnexion;
	
	
	@GetMapping("/qualification/creation")  // Accès au formulaire de création d'un qualification
	public String creer(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formQualif", new FormQualif());
		return Constants.CREATION_QUALIFICATION;
	}

	
	@PostMapping("/qualification/creation")  // Enregistrement des éléments de création d'une qualification
	public String enregistrerQualification(Model model, HttpSession session, FormQualif formQualif) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		Integer createurId = utilisateur.getId();
		formQualif.setCreateurId(createurId);
		microServiceLab.saveQualification(formQualif);
		return Constants.ESPACE_PERSONEL;
	}
	
	@GetMapping("/qualifications")
	public String qualifications(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.toutesLesQualifications();
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "1");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/historique/{id}")       // récupération de la liste de toutes les qualifications
										  // id = identifiant de l'utilisateur
	public String mesQualifications(@PathVariable (name = "id") Integer id, Model model, HttpSession session){
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualifications(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "2");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/qualifications/{id}")   // Récupérer les qualification d'un utilisateur, identifiant : id
											// id = identifiant de l'utilisateur
	public String qualificationsEnCours(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualificationsEnCours(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "3");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/qualification/{id}")  // Consulter une qualification
										// id = identifiant de l'utilisateur
	public String qualification(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualification);
		
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
	}
	
	@GetMapping("/essai/voir/{id}")
	public String visualiserEssais(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		
		List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(id);
		
		model.addAttribute("qualification", qualification);
		model.addAttribute("essais", essais);
		return Constants.PAGE_ESSAIS;
	}
	
	
}
