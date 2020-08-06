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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/echantillons")
public class EchantillonController {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/creer/{id}")
	public String creation(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formEchantillon", new FormEchantillon());
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualification);
		return Constants.CREATION_ECHANTILLON;
	}

	@PostMapping("/creer/{id}") // id = numero qualification
	public String enregistrementEchantillon(@PathVariable(name = "id") Integer id, Model model, HttpSession session,
			FormEchantillon formEchantillon, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.saveEchantillon(formEchantillon);

		redirectAttributes.addAttribute("id", id);

		return "redirect:/labplan/private/echantillons/voir";
		// return "ok";
	}

	@GetMapping("/voir/{id}")
	public String listeEchantillons(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(id);
		model.addAttribute("echantillons", echantillons);
		QualificationAux qualif = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualif);

		return Constants.ECHANTILLONS;

	}

	@GetMapping("/desactiver/{id}/{qualification}")
	public String desactiverEchatillon(@PathVariable(name = "id") Integer id, @PathVariable(name = "qualification") Integer qualification
			, Model model
			, HttpSession session
			, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.desactiverEchantillon(id, qualification);
		redirectAttributes.addAttribute("id", qualification);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(qualification);
		model.addAttribute("echantillons", echantillons);
		QualificationAux qualif = microServiceLab.obtenirQualification(qualification);
		model.addAttribute("qualification", qualif);
		
		return "redirect:/labplan/private/echantillons/voir";

	}
	
	@GetMapping("/voir")
	public String listeEchantillons2(@RequestParam(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(id);
		model.addAttribute("echantillons", echantillons);
		QualificationAux qualif = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualif);

		return Constants.ECHANTILLONS;

	}
	
	
	@GetMapping("/activer/{id}/{qualification}")
	public String activerEchatillon(@PathVariable(name = "id") Integer id, @PathVariable(name = "qualification") Integer qualification
			, Model model
			, HttpSession session
			, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.activerEchantillon(id, qualification);
		redirectAttributes.addAttribute("id", qualification);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(qualification);
		model.addAttribute("echantillons", echantillons);
		QualificationAux qualif = microServiceLab.obtenirQualification(qualification);
		model.addAttribute("qualification", qualif);
		
		return "redirect:/labplan/private/echantillons/voir";

	}
}