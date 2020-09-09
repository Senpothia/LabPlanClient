package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.model.FormDemande;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/demande")
public class DemandeController {
	
	@Autowired
	private UserConnexion userConnexion;
	
	@Autowired
	private MicroServiceLab microServiceLab;

	
	@GetMapping("/creation")
	public String creationDemande(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		FormDemande formDemande = new FormDemande();
		
		model.addAttribute("formDemande", formDemande);
		
		
		return "CreateDemande";
		
		}
	
	
	@PostMapping("/creation")
	public String enregistrerDemande(Model model, HttpSession session, FormDemande formDemande) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		System.out.println("id demandeur: " + utilisateur.getId()); 
		formDemande.setDemandeur(utilisateur.getId());
		microServiceLab.enregistrerDemande(formDemande);
		
		
		return "ok";
	}
		
		
	
	

}
