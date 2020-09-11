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

import com.michel.lab.model.FicheAux;
import com.michel.lab.model.FormFiche;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/fiche")
public class FicheController {
	
	@Autowired
	private UserConnexion userConnexion;
	
	@Autowired
	private MicroServiceLab microServiceLab;

	
	@GetMapping("/liste/{id}")
	public String listesFiches(@PathVariable("id") Integer numQualification
			, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		
		return "ok";
	}
	
	
	@GetMapping("/creation")
	public String listesFiches(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formFiche", new FormFiche());
		
		
		return "CreateFiche";
	}
	
	@PostMapping("/creation")
	public String listesFiches(Model model, HttpSession session, FormFiche formFiche) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formFiche.setAuteur(utilisateur.getId());
		microServiceLab.enregistrerFiche(formFiche);
		
		return "ok";
	}
	
	@GetMapping("/voir")
	public String voirLesFiches(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<FicheAux> fiches = microServiceLab.voirLesFiches();
		model.addAttribute("fiches", fiches);
		
		return "fiches";
	}
	
	
}
