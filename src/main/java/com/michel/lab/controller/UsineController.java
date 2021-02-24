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

import com.michel.lab.model.FormAnomalie;
import com.michel.lab.model.RepetitionAux;
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
	
	@GetMapping("/usine/non-conformites")
	public String selectionnerProduit(
			Model model, 
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		List<String> produits = microServiceLab.listeProduitsAvecAnomalie(token);
		model.addAttribute("produits", produits);
		return "selectionner_produit_anomalies";
	}
	
	@PostMapping("/usine/anomalies/selectionner/produit")
	public String afficherAnomaliesParProduit(Model model, 
			HttpSession session,
			String produit) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		List<FormAnomalie> anomalies = microServiceLab.obtenirAnomaliesParProduit(token, produit);
		model.addAttribute("anomalies", anomalies);
		return "anomalies";
	}
	
	@GetMapping("/usine/of/anomalies/{id}")
	public String anomaliesParOf(
			Model model, 
			HttpSession session,
			@PathVariable (name="id") Integer id) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		System.out.println("Visu anomalie pour OF");
		List<FormAnomalie> anomalies = microServiceLab.obtenirAnomalieParOf(token, id);
		model.addAttribute("anomalies", anomalies);
		return "anomalies_bilan_of";
		
	}
	

	@GetMapping("/usine/anomalie/selectionner/produit")
	public String selectionProduitAssocierOf(
			Model model, 
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		List<String> produits = microServiceLab.listeProduitsAvecAnomalie(token);
		model.addAttribute("produits", produits);
		return "selectionner_produit_association_of";
	}
	
	@PostMapping("/usine/anomalies/selectionner/produit/associer/of")
	public String afficherOfParProduit(Model model, 
			HttpSession session,
			String produit) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		RepetitionAux repetition = new RepetitionAux();
		repetition.setProduit(produit);
		List<FormOf> ofs = microServiceLab.obtenirOfsParProduit(token, produit);
		model.addAttribute("ofs", ofs);
		model.addAttribute("repetition", repetition);
	
		return "selectionner_of_association_produit";
	}
	
	@GetMapping("/usine/of/associer/{of}/{produit}")
	public String choisirAnomalie(Model model, 
			HttpSession session,
			@PathVariable (name="of") Integer of,
			@PathVariable (name="produit") String produit) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		RepetitionAux repetition = new RepetitionAux(null, null, of, produit, null);
		model.addAttribute("repetition", repetition);
		FormOf formOf = microServiceLab.obtenirOfParId(token, of);
		List<FormAnomalie> anomalies = microServiceLab.obtenirAnomaliesParProduit(token, produit);
		model.addAttribute("anomalies", anomalies);
		model.addAttribute("of", of);
		
		return "selectionner_anomalie";
	}
	
	@GetMapping("/usine/anomalie/selectionner/{anomalie}/{of}")
	public String ajouterRepetition(
			Model model, 
			HttpSession session,
			@PathVariable (name="anomalie") Integer idAnomalie,
			@PathVariable (name="of") Integer idOf) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		FormAnomalie formAnomalie = microServiceLab.obtenirAnomalieParId(token, idAnomalie);
		FormOf formOf  = microServiceLab.obtenirOfParId(token, idOf);
		model.addAttribute("formAnomalie", formAnomalie);
		model.addAttribute("formOf", formOf);
		model.addAttribute("repetition", new RepetitionAux(null, idAnomalie, idOf, null, null));
		
		return "ajouter_repetition";
	}
	
	@PostMapping("/usine/associer/of/anomalie/{anomalie}/{of}")
	public String enregistrerAssociationOfAnomalie(
			Model model, 
			HttpSession session,
			RepetitionAux repetition,
			@PathVariable(name="anomalie") Integer anomalie,
			@PathVariable(name="of") Integer of) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		repetition.setAnomalie(anomalie);
		repetition.setOf(of);
		
		microServiceLab.enregistrerRepetition(token, repetition);
		List<RepetitionAux> reps = microServiceLab.obtenirRepetitionsParOf(token, of);
		List<FormAnomalie> anomalies = new ArrayList<FormAnomalie>();
		for(RepetitionAux r: reps) {
			
			Integer id = r.getAnomalie();
			FormAnomalie f = microServiceLab.obtenirAnomalieParId(token, id);
			f.setTotal(r.getTotal());
			anomalies.add(f);
			
		}
		model.addAttribute("anomalies", anomalies);
		return "anomalies_bilan_of";
	}
	
	@GetMapping("/usine/modifier/of/{id}")
	public String modifierOf(
			Model model, 
			HttpSession session,
			@PathVariable(name = "id") Integer id) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		FormOf formOf = microServiceLab.obtenirOfParId(token, id);
		System.out.println("id formOf: " + formOf.getId());
		model.addAttribute("formOf", formOf);
		
		return "modifierOf";
	}
	
	@PostMapping("/usine/modifier/of/{id}")
	public String enreristrerModificationsOf(
					Model model, 
					HttpSession session,
					@PathVariable(name ="id" ) Integer id,
					FormOf formOf) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		formOf.setId(id);
		microServiceLab.modifierOf(token, formOf);
		
		List<FormOf> ofs = microServiceLab.obtenirListeOfs(token);
		model.addAttribute("ofs", ofs);
		return "ofs";
	
	}
	
	@GetMapping("/usine/supprimer/of/{id}")
	public String supprimerOf(Model model, 
			HttpSession session,
			@PathVariable(name = "id") Integer id) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		microServiceLab.supprimerOf(token, id);
		List<FormOf> ofs = microServiceLab.obtenirListeOfs(token);
		model.addAttribute("ofs", ofs);
		return "ofs";
	
		
	}
	
	@GetMapping("/usine/anomalie/modifier/{id}")
	public String modifierAnomalie(Model model, 
			HttpSession session,
			@PathVariable(name = "id") Integer id) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		FormAnomalie formAnomalie = microServiceLab.obtenirAnomalieParId(token, id);
		model.addAttribute("formAnomalie", formAnomalie);
		
		return "modifierAnomalie";
		
	}
	
	@PostMapping("/usine/anomalie/modifier/{id}")
	public String enregistrerModifierAnomalie(Model model, 
			HttpSession session,
			@PathVariable(name = "id") Integer id,
			FormAnomalie formAnomalie) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		formAnomalie.setId(id);
		microServiceLab.modifierAnomalie(token, formAnomalie);
		
		List<FormAnomalie> anomalies = microServiceLab.obtenirListeAnomalies(token);
		model.addAttribute("anomalies", anomalies);
		return "anomalies";
		
	}
	
	@GetMapping("/usine/anomalie/supprimer/{id}")
	public String supprimerAnomalie(Model model, 
			HttpSession session,
			@PathVariable(name = "id") Integer id) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		microServiceLab.supprimerAnomalie(token, id);
		List<FormAnomalie> anomalies = microServiceLab.obtenirListeAnomalies(token);
		model.addAttribute("anomalies", anomalies);
		return "anomalies";
	
	}
	
	
	
			

}
