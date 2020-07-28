package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.michel.lab.service.UserConnexion;
import com.michel.lab.model.FormEssai;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;

@Controller
@RequestMapping("/labplan/private/essai")
public class EssaiController {
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	@Autowired
	private UserConnexion userConnexion;
	
	@GetMapping("/creation")
	public String creationEssai(Model model, HttpSession session) {
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formEssai", new FormEssai());
		return "createEssai";
	}

}
