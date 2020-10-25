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

import com.michel.lab.model.FormAnomalie;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/gestion")
public class UsineController {
	
	@Autowired
	private UserConnexion userConnexion;

	@Autowired
	private MicroServiceLab microServiceLab;

	
	@GetMapping("/usine")
	public String accesActiviteSite(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return "accueil_gestion_usine";
	}
	
	@GetMapping("/usine/creer/of")
	public String creerOf(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formOf", new FormOf());
		return "createOf";
	}
	
	@PostMapping("/usine/creer/of")
	public String enregistrerOf(Model model, HttpSession session,
			FormOf formOf) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		Integer idUser = utilisateur.getId();
		formOf.setCreateur(idUser);
		
		microServiceLab.enregistrerOf(token, formOf);
		List<FormOf> ofs = microServiceLab.obtenirListeOfs(token);
		model.addAttribute("ofs", ofs);
		return "ofs";
	
	} 
	
	@GetMapping("/usine/of/voir/{id}")
	public String voirOf(
			@PathVariable(name="id") Integer id, Model model, HttpSession session) {
	
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		FormOf of = microServiceLab.obtenirOfParId(token, id);
		model.addAttribute("of", of);
		
		return "of";
	}
	
	@GetMapping("/usine/ofs/voir")
	public String VoirOfs(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		List<FormOf> ofs = microServiceLab.obtenirListeOfs(token);
		model.addAttribute("ofs", ofs);
		return "ofs";
		
	}
	
	@GetMapping("/usine/anomalie/declarer")
	public String declarerAnomalie(Model model, HttpSession session){
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		model.addAttribute("formAnomalie", new FormAnomalie());
		return "createAnomalie";
	}
	
	@PostMapping("/usine/anomalie/declarer")
	public String enregistrerAnomalie(Model model, HttpSession session,
			FormAnomalie formAnomalie){
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		Integer controleur = utilisateur.getId();
		formAnomalie.setControleur(controleur);
		microServiceLab.enregistrerAnomalie(token, formAnomalie);
		List<FormAnomalie> anomalies = microServiceLab.obtenirListeAnomalies(token);
		model.addAttribute("anomalies", anomalies);
		return "anomalies";
	}
	
	@GetMapping("/usine/anomalie/voir/{id}")
	public String voirAnomalie(
			@PathVariable(name="id") Integer id, 
			Model model, 
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		FormAnomalie anomalie = microServiceLab.obtenirAnomalieParId(token, id);
		model.addAttribute("anomalie", anomalie);
		
		return "anomalie";
	}
	
	@GetMapping("/non-conformites")
	public String selectionnerProduit() {
		
		return null;
	}

}
