package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.constants.Constants;
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

	@PostMapping("/creer/{id}")
	public String enregistrementEchantillon(@PathVariable(name = "id") Integer id, Model model, HttpSession session,
			FormEchantillon formEchantillon) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.saveEchantillon(formEchantillon);
		
		return "ok";
	}
}