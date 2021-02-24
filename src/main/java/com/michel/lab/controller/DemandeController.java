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


import com.michel.lab.model.DemandeAux;
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
	
	@GetMapping("/access")
	public String accessDemandes(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return "accueil_demandes";
	}

	@GetMapping("/creation")
	public String creationDemande(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		FormDemande formDemande = new FormDemande();
		// formDemande.setDemandeur(utilisateur.getId());
		model.addAttribute("formDemande", formDemande);

		return "CreateDemande";

	}

	@PostMapping("/creation")
	public String enregistrerDemande(Model model, HttpSession session, FormDemande formDemande) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		formDemande.setDemandeur(utilisateur.getId());
		formDemande.setStatut(true);
		microServiceLab.enregistrerDemande(token, formDemande);

		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";

		// return "ok";
	}

	@GetMapping("/liste")
	public String voirDemandes(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";

	}

	@GetMapping("/voir/{id}")
	public String VoirDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		DemandeAux demande = microServiceLab.voirDemande(token,id);
		model.addAttribute("demande", demande);

		return "demande2";

	}

	@GetMapping("/supprimer/{id}")
	public String supprimerDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		microServiceLab.supprimerDemande(token, id);
		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";

	}

	@GetMapping("/modifier/{id}")
	public String modifierDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		DemandeAux demande = microServiceLab.voirDemande(token, id);
		FormDemande formDemande = new FormDemande();

		formDemande.setId(id);
		formDemande.setNumero(demande.getNumero());
		formDemande.setDemandeur(utilisateur.getId());
		formDemande.setDate(demande.getDate());
		formDemande.setCode(demande.getCode());
		formDemande.setProduit(demande.getProduit());
		formDemande.setEchantillon(demande.getEchantillon());
		formDemande.setObjectif(demande.getObjectif());
		formDemande.setOrigine(demande.getOrigine());
		formDemande.setEssai(demande.getEssai());
		formDemande.setAuxiliaire(demande.getAuxiliaire());
		formDemande.setUrgence(demande.getUrgence());
		model.addAttribute("formDemande", formDemande);
		
		return "modifierDemande";

	}

	@PostMapping("/modifier/{id}")
	public String enregistrerModificationDemande(@PathVariable(name = "id") Integer id
			, Model model, HttpSession session
			, FormDemande formDemande) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		formDemande.setDemandeur(utilisateur.getId());
		System.out.println("Nom de produit: " + formDemande.getProduit());
		microServiceLab.modifierDemande(token, formDemande);
		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";
	}
	
	@GetMapping("/repondre/{id}")
	public String creationDemande(
			@PathVariable(name = "id") Integer id,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		DemandeAux demande = microServiceLab.voirDemande(token, id);
		
		System.out.println("num demande: " + demande.getNumero());
		FormDemande formDemande = new FormDemande();
		formDemande.setId(id);
		formDemande.setNumero(demande.getNumero());
		formDemande.setProduit(demande.getProduit());
		formDemande.setEchantillon(demande.getEchantillon());
		formDemande.setObjectif(demande.getObjectif());
		formDemande.setOrigine(demande.getOrigine());
		formDemande.setEssai(demande.getEssai());
		
		model.addAttribute("formDemande", formDemande);

		return "repondreDemande";

	}
	
	@PostMapping("/repondre/{id}")
	public String enregistrerReponseDemande(
			@PathVariable(name = "id") Integer id,
			Model model, HttpSession session,
			FormDemande formDemande) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		DemandeAux demande = microServiceLab.voirDemande(token, id);
		formDemande.setStatut(false);
		formDemande.setId(id);
		formDemande.setTechnicien(utilisateur.getId());
		microServiceLab.enregistrerReponse(token, formDemande);
		
		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";
		

	}
	
	@GetMapping("/traiter/{id}")
	public String traiterDemande(
			@PathVariable(name = "id") Integer id,
			Model model, HttpSession session) {
		
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		microServiceLab.traiterDemande(token, id);
		List<DemandeAux> demandes = microServiceLab.listeDemandes(token);
		model.addAttribute("demandes", demandes);

		return "demandes";
	}
	
	@GetMapping("/repondre/modifier/{id}")
	public String modifierReponseDemande(@PathVariable(name = "id") Integer id,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		DemandeAux demande = microServiceLab.voirDemande(token, id);
		System.out.println("num demande: " + demande.getNumero());
		FormDemande formDemande = new FormDemande();
		formDemande.setId(id);
		formDemande.setNumero(demande.getNumero());
		formDemande.setDate(demande.getDate());
		formDemande.setCode(demande.getCode());
		formDemande.setProduit(demande.getProduit());
		formDemande.setEchantillon(demande.getEchantillon());
		formDemande.setObjectif(demande.getObjectif());
		formDemande.setOrigine(demande.getOrigine());
		formDemande.setEssai(demande.getEssai());
		formDemande.setAuxiliaire(demande.getAuxiliaire());
		formDemande.setUrgence(demande.getUrgence());
		
		formDemande.setNomTechnicien(demande.getTechnicien());
		formDemande.setAvis(demande.getAvis());
		formDemande.setObservation(demande.getObservation());
		formDemande.setDateReponse(demande.getDateReponse());
		formDemande.setRapport(demande.getRapport());
		
		model.addAttribute("formDemande", formDemande);

		return "repondreDemande";

	}


}
