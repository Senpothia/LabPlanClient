package com.michel.lab.controller;

import java.time.format.DateTimeFormatter;
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
import com.michel.lab.model.QualificationAux;
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
	public String listesFiches(@PathVariable("id") Integer numQualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<FicheAux> fiches = microServiceLab.voirLesFichesParQualification(numQualification);
		model.addAttribute("fiches", fiches);
		model.addAttribute("qualification", numQualification);

		return "fichesParQualification";

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
		System.out.println("dégré: " + formFiche.getDegre());
		List<FicheAux> fiches = microServiceLab.voirLesFiches();
		model.addAttribute("fiches", fiches);

		return "fiches";
	}

	@GetMapping("/voir")
	public String voirLesFiches(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<FicheAux> fiches = microServiceLab.voirLesFiches();
		model.addAttribute("fiches", fiches);

		return "fiches";
	}

	@GetMapping("/ajouter/{qualification}")
	public String ajouterFicheQualification(@PathVariable("qualification") Integer numQualification, Model model,
			HttpSession session) {

		System.out.println("numQual: " + numQualification);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FormFiche formFiche = new FormFiche();
		formFiche.setQualification(numQualification);

		model.addAttribute("formFiche", formFiche);
		model.addAttribute("qualification", numQualification);

		return "ajouterFiche";

	}

	@PostMapping("/ajouter/{qualification}")
	public String ajouterFiches(@PathVariable("qualification") Integer numQualification, Model model,
			HttpSession session, FormFiche formFiche) {

		System.out.println("numQualif: " + numQualification);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formFiche.setAuteur(utilisateur.getId());
		formFiche.setQualification(numQualification);
		microServiceLab.ajouterFiche(formFiche);

		List<FicheAux> fiches = microServiceLab.voirLesFichesParQualification(numQualification);
		model.addAttribute("fiches", fiches);
		model.addAttribute("qualification", numQualification);

		return "fichesParQualification";

	}

	@GetMapping("/ajouter")
	public String ajouterFicheQualification(
			// @PathVariable("qualification") Integer numQualification,
			Model model, HttpSession session) {

		// System.out.println("numQual: " + numQualification);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FormFiche formFiche = new FormFiche();
		// formFiche.setQualification(numQualification);

		model.addAttribute("formFiche", formFiche);
		model.addAttribute("qualification", 0);

		return "creerFiche";

	}

	@PostMapping("/ajouter")
	public String ajouterFiches(
			// @PathVariable("qualification") Integer numQualification,
			Model model, HttpSession session, FormFiche formFiche) {

		// System.out.println("numQualif: " + numQualification);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formFiche.setAuteur(utilisateur.getId());
		// formFiche.setQualification(numQualification);
		microServiceLab.ajouterFiche(formFiche);

		List<FicheAux> fiches = microServiceLab.voirLesFiches();
		model.addAttribute("fiches", fiches);
		// model.addAttribute("qualification", numQualification);

		return "fiches";

	}

	@GetMapping("/voir/{id}")
	public String voirLaFiche(@PathVariable("id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FicheAux fiche = microServiceLab.voirLaFiches(id);

		model.addAttribute("fiche", fiche);

		return "fiche";
	}

	@GetMapping("/supprimer/{id}")
	public String supprimerLaFiche(@PathVariable("id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FicheAux fiche = microServiceLab.voirLaFiches(id);
		Integer numQualification = fiche.getNumQualification();
		System.out.println("numQualif pour suppression: " + numQualification);
		microServiceLab.supprimerLaFiches(id);

		if (numQualification != null) {

			List<FicheAux> fiches = microServiceLab.voirLesFichesParQualification(numQualification);
			model.addAttribute("fiches", fiches);
			model.addAttribute("qualification", numQualification);
			return "fichesParQualification";

		} else {

			List<FicheAux> fiches = microServiceLab.voirLesFiches();
			model.addAttribute("fiches", fiches);
			return "fiches";

		}

	}

	@GetMapping("/modifier/{id}")
	public String modifierLaFiche(@PathVariable("id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FicheAux fiche = microServiceLab.voirLaFiches(id);

		FormFiche formFiche = new FormFiche();

		formFiche.setId(fiche.getId());
		formFiche.setNumero(fiche.getNumero());
		formFiche.setAuteur(fiche.getAuteur());
		formFiche.setCirconstance(fiche.getCirconstance());
		formFiche.setCode(fiche.getCode());
		formFiche.setDate(fiche.getDate());
		formFiche.setDomaine(fiche.getDomaine());
		formFiche.setEtat(fiche.getEtat());
		formFiche.setIncidence(fiche.getIncidence());
		formFiche.setNiveau(fiche.getNiveau());
		formFiche.setObjet(fiche.getObjet());
		formFiche.setObservation(fiche.getObservation());
		formFiche.setProjet(fiche.getProjet());
		formFiche.setProduit(fiche.getProduit());
		formFiche.setQualification(fiche.getQualification());
		formFiche.setReponse(fiche.getReponse());
		formFiche.setSolution(fiche.getSolution());
		formFiche.setStatut(fiche.isStatut());

		model.addAttribute("formFiche", formFiche);

		return "modifierFiche";

	}

	@PostMapping("/modifier/{id}")
	public String enregistrerModificationFiche(@PathVariable("id") Integer id, Model model, HttpSession session,
			FormFiche formFiche) {

		System.out.println("Valeur id: " + id);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FicheAux fiche = microServiceLab.voirLaFiches(id);
		Integer numQualification = fiche.getNumQualification();
		formFiche.setId(id);
		formFiche.setAuteur(utilisateur.getId());
		microServiceLab.modifierLaFiche(formFiche);

		if (numQualification != null) {

			List<FicheAux> fiches = microServiceLab.voirLesFichesParQualification(numQualification);
			model.addAttribute("fiches", fiches);
			model.addAttribute("qualification", numQualification);

			return "fichesParQualification";

		} else {

			List<FicheAux> fiches = microServiceLab.voirLesFiches();
			model.addAttribute("fiches", fiches);
			return "fiches";

		}

	}

}
