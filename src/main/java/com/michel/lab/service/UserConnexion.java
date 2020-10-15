package com.michel.lab.service;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.michel.lab.model.Login;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.model.UtilisateurAux;
import com.michel.lab.proxy.MicroServiceLab;


@Service
public class UserConnexion {
	
	@Autowired
	MicroServiceLab microServiceLab;
	
	
	public Utilisateur identifierUtilisateur(Login login, HttpSession session) {
		
		System.out.println("Username: " + login.getUser());
		System.out.println("password: " + login.getPassword());
		
		try {
		ResponseEntity<UtilisateurAux> userBody = microServiceLab.generate(login);
		HttpStatus code = userBody.getStatusCode();
		System.out.println("code status: " + code);
		UtilisateurAux userAux = userBody.getBody();
		System.out.println("Token: " + userAux.getToken() );
		System.out.println("Nom de l'utilisateur récupéré: " + userAux.getNom());
		
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(userAux.getId());
		utilisateur.setNom(userAux.getNom());
		utilisateur.setPrenom(userAux.getPrenom());
		utilisateur.setUsername(userAux.getUsername());
		
		String token = userAux.getToken();
		
		session.setAttribute("USER", utilisateur);
		session.setAttribute("TOKEN", token);
		
		return utilisateur;
		} catch (Exception e) {
			
			System.out.println("non autorisé");
			return null;
		}
			
	}
	
	public Utilisateur obtenirUtilisateur (HttpSession session, Model model) {
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		if (utilisateur == null) {
			
			System.out.println("aucun utilisateur");
			model.addAttribute("authentification", false);
			
		}else {
			
			System.out.println("Nom de utilisateur session: " + utilisateur.getNom());
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);
			
		}
		return utilisateur;
		
	}

	

}
