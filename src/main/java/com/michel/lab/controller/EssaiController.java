package com.michel.lab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.michel.lab.service.UserConnexion;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.DomaineAux;
import com.michel.lab.model.FormEssai;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.ProcedureAux;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;

@Controller
@RequestMapping("/labplan/private/essai")
public class EssaiController {
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	@Autowired
	private UserConnexion userConnexion;
	
	@GetMapping("/creation")
	public String creationEssai(Model model, HttpSession session) {
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formEssai", new FormEssai());
		return "createEssai";
	}
	
	@GetMapping("/choix/{id}")
	public String choisir(@PathVariable (name = "id") Integer id,Model  model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		List<ProcedureAux> procedures = microServiceLab.obtenirProcedures();
		List<DomaineAux> domaines = microServiceLab.obtenirDomaines();
		System.out.println("Taille liste procedures: " + procedures.size()); 
		System.out.println("Taille liste domaines: " + domaines.size());
		
		model.addAttribute("domaines", domaines);
		model.addAttribute("qualification", id);
		
		return Constants.DOMAINES;
		
		
	}
	
	@GetMapping("/procedures/{id}")
	public String choisirEssais(@PathVariable (name = "id") Integer id,Model  model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<DomaineAux> domaines = microServiceLab.obtenirDomaines();
		
		DomaineAux domaine = domaines.get(id);
		Integer domaineId = domaine.getId(); 
		
		List<ProcedureAux> procedures = microServiceLab.obtenirProceduresParDomaine(domaineId);
		
		System.out.println("taille liste procedures : " + procedures.size());
		model.addAttribute("procedures", procedures);
		model.addAttribute("qualification", id);
		return Constants.ESSAIS;
		
		
	}
}
