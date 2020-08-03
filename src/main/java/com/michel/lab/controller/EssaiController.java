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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		return Constants.CREATION_ESSAI;
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
	
	@GetMapping("/procedures/{id}/{qualification}")  // id = domaine, qualification = numéro de qualification
	public String choisirEssais(@PathVariable (name = "id") Integer id, @PathVariable (name = "qualification") Integer qualification,Model  model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		List<ProcedureAux> procedures = microServiceLab.obtenirProceduresParDomaine(id);
		
		System.out.println("taille liste procedures : " + procedures.size());
		model.addAttribute("procedures", procedures);
		model.addAttribute("qualification", qualification);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("domaine",id);
		return Constants.ESSAIS;
	
	}
	
	@GetMapping("/procedures/ajouter/{id}/{domaine}/{qualification}")
	public String ajouterProcedure(@PathVariable (name = "id") Integer id, 
			@PathVariable (name = "domaine") Integer domaineId,
			@PathVariable (name = "qualification") Integer qualification,
			RedirectAttributes redirectAttributes,
			Model  model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		Integer idUser = utilisateur.getId();
		microServiceLab.ajouterProcedure(id, qualification, idUser);
		
		redirectAttributes.addAttribute("id", id);
		redirectAttributes.addAttribute("domaine", domaineId);
		redirectAttributes.addAttribute("qualification", qualification);
		return "redirect:/labplan/private/essai/procedures";
	}
	
	@GetMapping("/procedures")  // id = domaine, qualification = numéro de qualification
	public String choisirEssais2(@RequestParam (name = "id") Integer id,
			@RequestParam (name = "domaine") Integer domaineId,
			@RequestParam (name = "qualification") Integer qualification,
			
			Model  model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		List<ProcedureAux> procedures = microServiceLab.obtenirProceduresParDomaine(domaineId);
		
		System.out.println("taille liste procedures : " + procedures.size());
		model.addAttribute("procedures", procedures);
		model.addAttribute("qualification", qualification);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("domaine", domaineId);
		
		return Constants.ESSAIS;
		
	}
	
	
}
