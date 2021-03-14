package com.michel.lab.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/private/echantillons")
public class EchantillonController {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/creer/{id}")
	public String creation(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			model.addAttribute("formEchantillon", new FormEchantillon());
			QualificationAux qualification = microServiceLab.obtenirQualification(token, id);
			model.addAttribute("qualification", qualification);
			
			return Constants.CREATION_ECHANTILLON;
				

			} else {

				return "redirect:/labplan/connexion";
			}
		
		
		
	}

	@PostMapping("/creer/{id}") // id = numero qualification
	public String enregistrementEchantillon(@PathVariable(name = "id") Integer id, Model model, HttpSession session,
			FormEchantillon formEchantillon, RedirectAttributes redirectAttributes) {

		System.out.println("Numéro de qualification pour enregistrement ech: " + id);
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			formEchantillon.setQualification(id);
			System.out.println(
					"Numéro de qualification pour enregistrement ech dans form: " + formEchantillon.getQualification());
			microServiceLab.saveEchantillon(token, formEchantillon);

			redirectAttributes.addAttribute("id", id);

			return "redirect:/labplan/private/echantillons/voir";
				

			} else {

				return "redirect:/labplan/connexion";
			}
		

	}

	@GetMapping("/voir/{id}")
	public String listeEchantillons(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, id);
			model.addAttribute("echantillons", echantillons);
			QualificationAux qualif = microServiceLab.obtenirQualification(token, id);
			model.addAttribute("qualification", qualif);
			return Constants.ECHANTILLONS;

				

			} else {

				return "redirect:/labplan/connexion";
			}
	
	}

	@GetMapping("/desactiver/{id}/{qualification}")
	public String desactiverEchatillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
	

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			microServiceLab.desactiverEchantillon(token, id, qualification);
			redirectAttributes.addAttribute("id", qualification);
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, qualification);
			model.addAttribute("echantillons", echantillons);
			QualificationAux qualif = microServiceLab.obtenirQualification(token, qualification);
			model.addAttribute("qualification", qualif);
			return "redirect:/labplan/private/echantillons/voir";
				

			} else {

				return "redirect:/labplan/connexion";
			}
		

	}

	@GetMapping("/voir")
	public String listeEchantillons2(@RequestParam(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, id);
			model.addAttribute("echantillons", echantillons);
			QualificationAux qualif = microServiceLab.obtenirQualification(token, id);
			model.addAttribute("qualification", qualif);
			return Constants.ECHANTILLONS;


			} else {

				return "redirect:/labplan/connexion";
			}
		
	}

	@GetMapping("/activer/{id}/{qualification}")
	public String activerEchatillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			microServiceLab.activerEchantillon(token, id, qualification);
			redirectAttributes.addAttribute("id", qualification);
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, qualification);
			model.addAttribute("echantillons", echantillons);
			QualificationAux qualif = microServiceLab.obtenirQualification(token, qualification);
			model.addAttribute("qualification", qualif);
			return "redirect:/labplan/private/echantillons/voir";
				

			} else {

				return "redirect:/labplan/connexion";
			}
		

	}

	@GetMapping("/modifier/{id}/{qualification}")
	public String modifierEchantillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		if (testUser(utilisateur)) {
			
			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			EchantillonAux echantillon = microServiceLab.obtenirEchantillon(token, id);

			FormEchantillon formEchantillon = new FormEchantillon();
			formEchantillon.setCaracteristique(echantillon.getCaracteristique());
			formEchantillon.setNumero(echantillon.getNumero());
			formEchantillon.setVersion(echantillon.getVersion());
			formEchantillon.setId(id);

			String date = echantillon.getDate();

			System.out.println("Date échantillon récupérée:" + date);

			model.addAttribute("formEchantillon", formEchantillon);
			model.addAttribute("echantillon", id);
			model.addAttribute("qualification", qualification);

			return Constants.MODIFIER_ECHANTILLON;

				

			} else {

				return "redirect:/labplan/connexion";
			}
		
	}

	@PostMapping("/modifier/{id}/{qualification}")
	public String enregistrerEchantillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session,
			FormEchantillon formEchantillon, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			formEchantillon.setId(id);
			microServiceLab.modifierEchantillon(token, formEchantillon);
			System.out.println("id récupéré: " + id);
			System.out.println("qualif récupéré: " + qualification);

			redirectAttributes.addAttribute("id", qualification);
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, qualification);
			model.addAttribute("echantillons", echantillons);
			QualificationAux qualif = microServiceLab.obtenirQualification(token, qualification);
			model.addAttribute("qualification", qualif);
			return "redirect:/labplan/private/echantillons/voir";
				

			} else {

				return "redirect:/labplan/connexion";
			}
		
		
	}

	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}
}