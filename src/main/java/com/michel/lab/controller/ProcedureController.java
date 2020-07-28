package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.michel.lab.service.UserConnexion;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.FormProcedure;
import com.michel.lab.model.FormQualif;
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
	public String creationProcedure(Model model, HttpSession session) {
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formProcedure", new FormProcedure());
		return "createProcedure";

	}
	
	@PostMapping("/procedure/creation")
	public String enregistrerProcedure(Model model, HttpSession session, FormProcedure formProcedure) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
	
		microServiceLab.saveProcedure(formProcedure);
		return Constants.ESPACE_PERSONEL;
		
	}

}
