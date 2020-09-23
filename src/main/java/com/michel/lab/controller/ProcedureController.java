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
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.lab.service.UserConnexion;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.FormProcedure;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.ProcedureAux;
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
	public String creationProcedure(
			@RequestParam(name = "selection", defaultValue = "true", required = false) boolean selection, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;

		model.addAttribute("formProcedure", new FormProcedure());
		// List<String> nomsDomaines = microServiceLab.tousLesDomaines();
		// model.addAttribute("domaines", nomsDomaines);

		if (!selection) {

			model.addAttribute("selection", false);

		} else {

			List<String> nomsDomaines = microServiceLab.tousLesDomaines(token);
			model.addAttribute("domaines", nomsDomaines);
			model.addAttribute("selection", true);

		}

		return Constants.CREATION_PROCEDURE;

	}

	@PostMapping("/procedure/creation")
	public String enregistrerProcedure(String domaine, Model model, HttpSession session, FormProcedure formProcedure) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		System.out.println("Domaine récupéré: " + domaine);
		microServiceLab.saveProcedure(token, formProcedure);
		return Constants.ESPACE_PERSONEL;

	}

	@GetMapping("/procedure/liste/voir")
	public String voirProcedure(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		List<String> nomsDomaines = microServiceLab.tousLesDomaines(token);
		model.addAttribute("domaines", nomsDomaines);
		model.addAttribute("formProcedure", new FormProcedure());

		return "choisirProcedure";

	}

	@PostMapping("/procedure/liste/voir")
	public String listerProcedures(String domaine, Model model, HttpSession session, FormProcedure formProcedure) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		System.out.println("Domaine récupéré: " + domaine);
		List<ProcedureAux> procedures = microServiceLab.obtenirProceduresParDomaine(token, domaine);
		for (ProcedureAux p : procedures) {

			System.out.println("Nom procédure: " + p.getNom());
		}
		model.addAttribute("procedures", procedures);

		return "procedures";

	}

	@GetMapping("/procedure/modifier/{id}")
	public String modifierProcedure(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		ProcedureAux procedure = microServiceLab.obtenirUneProcedure(token, id);

		FormProcedure formProcedure = new FormProcedure();

		formProcedure.setDomaine(procedure.getDomaine());
		formProcedure.setNom(procedure.getNom());
		formProcedure.setReferentiel(procedure.getReferentiel());
		formProcedure.setVersion(procedure.getVersion());

		model.addAttribute("formProcedure", formProcedure);
		model.addAttribute("id", id);
		return "modifierProcedure";

	}

	@PostMapping("/procedure/modifier/{id}")
	public String enregistrerModificationProcedure(@PathVariable(name = "id") Integer id, Model model,
			HttpSession session, FormProcedure formProcedure) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		formProcedure.setId(id);
		microServiceLab.modifierProcedure(token, formProcedure);
		
		List<ProcedureAux> procedures = microServiceLab.obtenirProceduresParDomaine(token, formProcedure.getDomaine());
		for (ProcedureAux p : procedures) {

			System.out.println("Nom procédure: " + p.getNom());
		}
		model.addAttribute("procedures", procedures);

		return "procedures";
		
	}

}
