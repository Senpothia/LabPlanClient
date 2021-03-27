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
import com.michel.lab.model.Groupe;
import com.michel.lab.model.ProcedureAux;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;

@Controller
@RequestMapping("/private/essai")
public class EssaiController {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/creation")
	public String creationEssai(Model model, HttpSession session) {
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		
		if (testUser(utilisateur)) {
			
			model.addAttribute("formEssai", new FormEssai());
			return Constants.CREATION_ESSAI;
				

			} else {

				return "redirect:/connexion";
			}
		
	}

	@GetMapping("/choix/{id}")
	public String choisir(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			QualificationAux qualification = microServiceLab.obtenirQualification(token, id);
			List<ProcedureAux> procedures = microServiceLab.obtenirProcedures(token);
			List<DomaineAux> domaines = microServiceLab.obtenirDomaines(token);
			System.out.println("Taille liste procedures: " + procedures.size());
			System.out.println("Taille liste domaines: " + domaines.size());

			model.addAttribute("domaines", domaines);
			model.addAttribute("qualification", id);
			return Constants.DOMAINES;

			} else {

				return "redirect:/connexion";
			}
		

	}

	@GetMapping("/procedures/{id}/{qualification}") // id = domaine, qualification = numéro de qualification
	public String choisirEssais(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session) {
		
		System.out.println("*** entrée méthode choisirEssais ***");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		/*
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;

		System.out.println("id domaine: " + id);
		System.out.println("id qualification: " + qualification);

		List<ProcedureAux> procedures0 = microServiceLab.obtenirProceduresParDomaine(token, id);  // liste de toutes les procédures du domaine
		List<Integer> listeIdProcedures = new ArrayList<Integer>();

		for (ProcedureAux pro : procedures0) {  // Récupération de tous les id de toutes les procédures existantes dans le domaine

			Integer idPro = pro.getId();
			listeIdProcedures.add(idPro);
		}
		
		System.out.println("Taille liste listeIdProcedure: " + listeIdProcedures.size());
		
		Groupe groupe = new Groupe(id, qualification);
		List<Integer> idProcedures = microServiceLab.obtenirSelectionProcedure(token, groupe); // procédures sélectionnées pour
																						// la qualification
		System.out.println("Taille liste des procédures sélectionnées pour la qualification choisie: " + idProcedures.size());
		
		for(Integer iden: idProcedures) {
			
			System.out.println("identifiant de procédure sélectionné: " + iden);
		}
		List<ProcedureAux> procedures = new ArrayList<ProcedureAux>();
		//boolean isPresent ;
		
		for (Integer proId : listeIdProcedures) {

			//Integer identifiantProcedure = proId;
			System.out.println("identifiantProcedure boucle for: " + proId);
		//	isPresent = idProcedures.contains(identifiantProcedure);
			
			if (idProcedures.contains(proId)) {
				
				ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
				procedure.setActif(false);
				procedures.add(procedure);
				//pro.setActif(true);
				
			} else {
				
				ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
				procedure.setActif(true);
				procedures.add(procedure);
				//pro.setActif(false);
			}
			
			//procedures.add(pro);
			

		}
		
		for (ProcedureAux pro: procedures) {
			
			System.out.println(pro.toString());
		}
		System.out.println("taille liste procedures : " + procedures0.size());
		System.out.println("taille liste idProcedure des id de procédures sélectionnées : " + idProcedures.size());
		model.addAttribute("procedures", procedures);
		model.addAttribute("qualification", qualification);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("domaine", id);
		*/
		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			System.out.println("id domaine: " + id);
			System.out.println("id qualification: " + qualification);

			List<ProcedureAux> procedures0 = microServiceLab.obtenirProceduresParDomaine(token, id);  // liste de toutes les procédures du domaine
			List<Integer> listeIdProcedures = new ArrayList<Integer>();

			for (ProcedureAux pro : procedures0) {  // Récupération de tous les id de toutes les procédures existantes dans le domaine

				Integer idPro = pro.getId();
				listeIdProcedures.add(idPro);
			}
			
			System.out.println("Taille liste listeIdProcedure: " + listeIdProcedures.size());
			
			Groupe groupe = new Groupe(id, qualification);
			List<Integer> idProcedures = microServiceLab.obtenirSelectionProcedure(token, groupe); // procédures sélectionnées pour
																							// la qualification
			System.out.println("Taille liste des procédures sélectionnées pour la qualification choisie: " + idProcedures.size());
			
			for(Integer iden: idProcedures) {
				
				System.out.println("identifiant de procédure sélectionné: " + iden);
			}
			List<ProcedureAux> procedures = new ArrayList<ProcedureAux>();
			//boolean isPresent ;
			
			for (Integer proId : listeIdProcedures) {

				//Integer identifiantProcedure = proId;
				System.out.println("identifiantProcedure boucle for: " + proId);
			//	isPresent = idProcedures.contains(identifiantProcedure);
				
				if (idProcedures.contains(proId)) {
					
					ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
					procedure.setActif(false);
					procedures.add(procedure);
					//pro.setActif(true);
					
				} else {
					
					ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
					procedure.setActif(true);
					procedures.add(procedure);
					//pro.setActif(false);
				}
				
				//procedures.add(pro);
				

			}
			
			for (ProcedureAux pro: procedures) {
				
				System.out.println(pro.toString());
			}
			System.out.println("taille liste procedures : " + procedures0.size());
			System.out.println("taille liste idProcedure des id de procédures sélectionnées : " + idProcedures.size());
			model.addAttribute("procedures", procedures);
			model.addAttribute("qualification", qualification);
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("domaine", id);
			return Constants.ESSAIS;

				

			} else {

				return "redirect:/connexion";
			}
		
	}

	@GetMapping("/procedures/ajouter/{id}/{domaine}/{qualification}")
	public String ajouterProcedure(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "domaine") Integer domaineId,
			@PathVariable(name = "qualification") Integer qualification, RedirectAttributes redirectAttributes,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
	
		
		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			Integer idUser = utilisateur.getId();
			microServiceLab.ajouterProcedure(token, id, qualification, idUser);

			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("domaine", domaineId);
			redirectAttributes.addAttribute("qualification", qualification);
			//return "redirect:/labplan/private/essai/procedures";
			return "redirect:/private/essai/procedures";

			} else {

				return "redirect:/connexion";
			}
		
	}
	
	@GetMapping("/procedures/supprimer/{id}/{domaine}/{qualification}")
	public String supprimerProcedure(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "domaine") Integer domaineId,
			@PathVariable(name = "qualification") Integer qualification, RedirectAttributes redirectAttributes,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
	
		
		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			Integer idUser = utilisateur.getId();
			microServiceLab.supprimerProcedure(token, id, qualification, idUser);

			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("domaine", domaineId);
			redirectAttributes.addAttribute("qualification", qualification);
			//return "redirect:/labplan/private/essai/procedures";
			return "redirect:/private/essai/procedures";

			} else {

				return "redirect:/connexion";
			}
		
	}

	@GetMapping("/procedures") // id = domaine, qualification = numéro de qualification
	public String choisirEssais2(@RequestParam(name = "id") Integer id,  // id = identifiant procédure
			@RequestParam(name = "domaine") Integer domaineId,
			@RequestParam(name = "qualification") Integer qualification,

			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		
		
		if (testUser(utilisateur)) {

			token = "Bearer " + token;
			System.out.println("*** méthode choisoirEssai2 *** ");
			System.out.println("Avant");
			System.out.println("id domaine: " + domaineId);
			System.out.println("id qualification: " + qualification);

			List<ProcedureAux> procedures0 = microServiceLab.obtenirProceduresParDomaine(token, domaineId);  // liste de toutes les procédures du domaine
			List<Integer> listeIdProcedures = new ArrayList<Integer>();

			for (ProcedureAux pro : procedures0) {  // Récupération de tous les id de toutes les procédures existantes dans le domaine

				Integer idPro = pro.getId();
				listeIdProcedures.add(idPro);
			}
			
			System.out.println("Taille liste listeIdProcedure: " + listeIdProcedures.size());
			
			Groupe groupe = new Groupe(domaineId, qualification);
			List<Integer> idProcedures = microServiceLab.obtenirSelectionProcedure(token, groupe); // procédures sélectionnées pour
																							// la qualification
			System.out.println("Taille liste des procédures sélectionnées pour la qualification choisie: " + idProcedures.size());
			
			for(Integer iden: idProcedures) {
				
				System.out.println("identifiant de procédure sélectionné: " + iden);
			}
			List<ProcedureAux> procedures = new ArrayList<ProcedureAux>();
			//boolean isPresent ;
			
			for (Integer proId : listeIdProcedures) {

				//Integer identifiantProcedure = proId;
				System.out.println("identifiantProcedure boucle for: " + proId);
			//	isPresent = idProcedures.contains(identifiantProcedure);
				
				if (idProcedures.contains(proId)) {
					
					ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
					procedure.setActif(false);
					procedures.add(procedure);
					//pro.setActif(true);
					
				} else {
					
					ProcedureAux procedure = procedures0.get(listeIdProcedures.indexOf(proId));
					procedure.setActif(true);
					procedures.add(procedure);
					//pro.setActif(false);
				}
				
				//procedures.add(pro);
				

			}
			
			for (ProcedureAux pro: procedures) {
				
				System.out.println(pro.toString());
			}
			
			System.out.println("taille liste procedures : " + procedures0.size());
			System.out.println("taille liste idProcedure des id de procédures sélectionnées : " + idProcedures.size());
			
			model.addAttribute("procedures", procedures);
			model.addAttribute("qualification", qualification);
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("domaine", domaineId);
			return Constants.ESSAIS;
				

			} else {

				return "redirect:/connexion";
			}
		

	}
	
	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

}
