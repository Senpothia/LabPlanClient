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

		formDemande.setDemandeur(utilisateur.getId());
		microServiceLab.enregistrerDemande(formDemande);

		List<DemandeAux> demandes = microServiceLab.listeDemandes();
		model.addAttribute("demandes", demandes);

		return "demandes";

		// return "ok";
	}

	@GetMapping("/liste")
	public String voirDemandes(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<DemandeAux> demandes = microServiceLab.listeDemandes();
		model.addAttribute("demandes", demandes);

		return "demandes";

	}

	@GetMapping("/voir/{id}")
	public String VoirDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		DemandeAux demande = microServiceLab.voirDemande(id);
		model.addAttribute("demande", demande);

		return "demande";

	}

	@GetMapping("/supprimer/{id}")
	public String supprimerDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.supprimerDemande(id);
		List<DemandeAux> demandes = microServiceLab.listeDemandes();
		model.addAttribute("demandes", demandes);

		return "demandes";

	}

	@GetMapping("/modifier/{id}")
	public String modifierDemande(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		DemandeAux demande = microServiceLab.voirDemande(id);
		FormDemande formDemande = new FormDemande();

		model.addAttribute("formDemande", formDemande);
		formDemande.setId(id);
		formDemande.setNumero(demande.getNumero());
		formDemande.setDemandeur(utilisateur.getId());
		formDemande.setEchantillon(demande.getEchantillon());

		return "modifierDemande";

	}

	@PostMapping("/modifier/{id}")
	public String enregistrerModificationDemande(@PathVariable(name = "id") Integer id
			, Model model, HttpSession session
			, FormDemande formDemande) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formDemande.setDemandeur(utilisateur.getId());
		microServiceLab.modifierDemande(formDemande);
		List<DemandeAux> demandes = microServiceLab.listeDemandes();
		model.addAttribute("demandes", demandes);

		return "demandes";
	}

}
