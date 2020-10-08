package com.michel.lab.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.model.FormSite;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/activite")
public class SiteController {
	
	@Autowired
	private UserConnexion userConnexion;
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	@GetMapping("/site")
	public String accesActiviteSite(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return "accueil_activite_site";
	}
	
	@GetMapping("/site/creer")
	public String creerSite(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formSite", new FormSite());
		return "createSite";
	}
	
	
	@PostMapping("/site/creer")
	public String enregistrerSite(Model model, HttpSession session, FormSite formSite) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		formSite.setCommercial(utilisateur.getId());
		microServiceLab.enregistrerSite(token, formSite);
		List<FormSite> sites = microServiceLab.obtenirListeSites(token);
 		model.addAttribute("sites", sites);
		return "sites";
	}
	
	@GetMapping("/site/incidents")
	public String voirIncidents(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		List<FormSite> sites = microServiceLab.obtenirListeSites(token);
 		model.addAttribute("sites", sites);
		return "sites";
		
	}
	
	@GetMapping("/site/declarer")
	public String declarerIncident(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formIncident", new FormIncident());
		return "createIncident";
	}
	
	

}
