package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.FormCompte;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan")

public class HomeController {
	/*
	@Autowired
	private MicroServiceLab microServiceLab;
	*/
	@Autowired
	private UserConnexion userConnexion;
	
	
	@GetMapping("/")
	public String accueil(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PAGE_ACCUEIL;
	}
	
	@GetMapping("/aide")
	public String aide(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.AIDE;
	}
	
	@GetMapping("/presentation")
	public String presentation(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PRESENTATION;
	}
	
	@GetMapping("/connexion")
	public String connexion(Model model, HttpSession session) {
		
	     	Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
			return Constants.CONNEXION;
		}
	
	@GetMapping("/compte")   // Accès formulaire de création de compte
	public String compte(Model model) {
		
		FormCompte formCompte = new FormCompte();
		model.addAttribute("formCompte", formCompte);
		return Constants.CREATION_COMPTE;
	}
	
	@PostMapping("/compte")  // Création du compte
	public String creationCompte(Model model, FormCompte formCompte) {
		/*
		UtilisateurAux utilisateurAux = new UtilisateurAux();
		utilisateurAux.setPrenom(formCompte.getPrenom());
		utilisateurAux.setNom(formCompte.getNom());
		utilisateurAux.setToken(formCompte.getPassword());
		utilisateurAux.setUsername(formCompte.getUsername());
		utilisateurAux.setRole("USER");
		*/
		//microServiceOuvrages.creerCompte(utilisateurAux);
			
		return Constants.CONNEXION;
	}
	
	@GetMapping("/compte/modifier")
	public String modifierCompte(@RequestParam(name = "error", required = false) boolean error,Model model, HttpSession session) {
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		FormCompte formCompte = new FormCompte();
		formCompte.setNom(utilisateur.getNom());
		formCompte.setPrenom(utilisateur.getPrenom());
		formCompte.setUsername(utilisateur.getUsername());
		model.addAttribute("formCompte", formCompte);
		model.addAttribute("authentification", true);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("error", error);
		
		return Constants.MODIFIER_COMPTE;
	}
	
}
