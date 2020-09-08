package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.model.Utilisateur;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/fiche")
public class FicheController {
	
	@Autowired
	private UserConnexion userConnexion;
	
	@GetMapping("/liste/{id}")
	public String listesFiches(@PathVariable("id") Integer numQualification
			, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		
		return "ok";
	}
}
