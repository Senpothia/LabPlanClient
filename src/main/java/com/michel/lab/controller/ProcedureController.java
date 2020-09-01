package com.michel.lab.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.lab.service.UserConnexion;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.FormProcedure;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;

@Controller
@RequestMapping("/labplan/private")
public class ProcedureController {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/procedure/creation")
	public String creationProcedure(@RequestParam(name = "selection", defaultValue = "true", required = false) boolean selection,Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		model.addAttribute("formProcedure", new FormProcedure());
		//List<String> nomsDomaines = microServiceLab.tousLesDomaines();
		//model.addAttribute("domaines", nomsDomaines);
		
		if (!selection) {
			
			model.addAttribute("selection", false);
			
		} else {
			
			List<String> nomsDomaines = microServiceLab.tousLesDomaines();
			model.addAttribute("domaines", nomsDomaines);
			model.addAttribute("selection", true);
		
		}
		
		return Constants.CREATION_PROCEDURE;

	}
	
	@PostMapping("/procedure/creation")
	public String enregistrerProcedure(String domaine, Model model, HttpSession session, FormProcedure formProcedure) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		System.out.println("Domaine récupéré: " + domaine);
		microServiceLab.saveProcedure(formProcedure);
		return Constants.ESPACE_PERSONEL;
		
	}
	
	@GetMapping("/procedure/voir")
	public String voirProcedure(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return "ok";
		
	}

}
