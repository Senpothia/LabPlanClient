package com.michel.lab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michel.lab.model.FormDemande;
import com.michel.lab.model.FormSite;
import com.michel.lab.model.RecurrenceAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/private/activite")
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
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		model.addAttribute("formIncident", new FormIncident());
		return "createIncident";
	}

	@PostMapping("/site/declarer")
	public String enregistrerIncident(Model model, HttpSession session, FormIncident formIncident) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		formIncident.setCommercial(utilisateur.getId());
		microServiceLab.enregistrerIncident(token, formIncident);
		List<FormIncident> defauts = microServiceLab.obtenirListeIncident(token);
		model.addAttribute("defauts", defauts);

		return "defauts0";
	}

	@GetMapping("/site/defaut/selectionner/produit")
	public String selectionnerProduit(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;

		List<FormIncident> defauts = microServiceLab.obtenirListeIncident(token);

		List<String> nomProduits = new ArrayList<String>();
		for (FormIncident d : defauts) {

			String nom = d.getProduit();
			nomProduits.add(nom);
		}
		
		model.addAttribute("produits", nomProduits);

		return "selectionner_produit";
	}
	
	
	@PostMapping("/site/defaut/selectionner/produit")
	public String selectionProduit(Model model, HttpSession session, String produit) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		List<FormIncident> defauts = microServiceLab.obtenirDefautParProduit(token, produit);
		System.out.println("Id defaut: " + defauts.get(0).getId());
		model.addAttribute("defauts", defauts);
		return "selectionner_defaut";
	}
	
	
	@GetMapping("/site/defaut/voir/{id}")  // version initiale
	public String voirDefaut(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		FormIncident defaut = microServiceLab.obtenirDefautParId(token, id);
		model.addAttribute("formIncident", defaut);
		return "presentation_defaut";
	}

	
	@GetMapping("/site/defaut/voir/{defaut}/{site}")   // en cours d'écriture
	public String voirDefautRepetitions(@PathVariable(name = "defaut") Integer idDefaut, @PathVariable(name = "site") Integer idSite, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		FormIncident incident = microServiceLab.obtenirDefautParId(token, idDefaut);
		incident.setSite(idSite);
		FormIncident defaut = microServiceLab.obtenirDefautParIdPourProduit(token, incident);
		model.addAttribute("formIncident", defaut);
		return "presentation_defaut2";
	}
	
	
	@GetMapping("/site/defaut/associer")
	public String associerIncident(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;

		List<FormIncident> defauts = microServiceLab.obtenirListeIncident(token);

		List<String> nomDefauts = new ArrayList<String>();
		for (FormIncident d : defauts) {

			String nom = d.getDescription();
			nomDefauts.add(nom);
		}

		List<FormSite> sites = microServiceLab.obtenirListeSites(token);

		List<String> nomSites = new ArrayList<String>();
		for (FormSite s : sites) {

			String nom = s.getNom();
			nomSites.add(nom);
		}

		model.addAttribute("sites", nomSites);
		model.addAttribute("defauts", nomDefauts);

		return "ok";
	}

	@GetMapping("/site/defauts/voir/{id}")
	public String voirIncidentsId(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		FormSite site = microServiceLab.obtenirSiteParId(token, id);
		
		List<FormIncident> defauts = microServiceLab.obtenirDefautsParSite(token, id);
		model.addAttribute("defauts", defauts);
		model.addAttribute("site", site);
		
		return "defauts";
	}
	
	
	@GetMapping("/site/defaut/selectionner/{id}")
	public String selectionnerIncidentsId(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		List<FormSite> sites = microServiceLab.obtenirListeSites(token);
		model.addAttribute("sites", sites);
		model.addAttribute("defaut", id);
		return "selectionner_site";

	}
	
	@GetMapping("/site/defaut/selectionner/{id}/{defaut}")
	public String associerSiteDefaut(
			@PathVariable(name = "id") Integer idSite,
			@PathVariable(name = "defaut") Integer idDefaut,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		System.out.println("idSite: " + idSite);
		System.out.println("idDefaut: " + idDefaut);
		FormIncident defaut = microServiceLab.obtenirDefautParId(token, idDefaut);
		FormSite site = microServiceLab.obtenirSiteParId(token, idSite);
		
		RecurrenceAux recurrence = new RecurrenceAux(idSite, idDefaut, 0);
		
		System.out.println("idSite obj: " + recurrence.getSite());
		
		model.addAttribute("site", site);
		model.addAttribute("defaut", defaut);
		model.addAttribute("recurrence", recurrence);
		
		return "ajouter_recurrence";
	}
	
	@PostMapping("/site/defaut/selectionner/{id}/{defaut}")
	public String EnregistrerAssocierSiteDefaut(
			@PathVariable(name = "id") Integer idSite,
			@PathVariable(name = "defaut") Integer idDefaut,
			Model model, HttpSession session,
			RecurrenceAux recurrence) {
		

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		recurrence.setSite(idSite);
		
		microServiceLab.ajouterRecurrence(token, recurrence);
		
		FormIncident defaut = microServiceLab.obtenirDefautParId(token, idDefaut);
		List<FormSite> sites = microServiceLab.cartographier(token, idDefaut);
		model.addAttribute("sites", sites);
		model.addAttribute("defaut", defaut);
		
		
		return "cartographie";
	}

}
