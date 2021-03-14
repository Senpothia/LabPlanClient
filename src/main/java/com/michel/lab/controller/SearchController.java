package com.michel.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.model.Utilisateur;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	
	
	@PostMapping("/simple")
	public String searchSimple(String phrase) {
		
		System.out.println("pharse: " + phrase);
		
		return "ok";
	}
	
	@GetMapping("/avancee")
	public String searchAvancee(String phrase) {
		
		
		return "ok";
	}
	
	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

}
