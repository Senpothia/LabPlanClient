package com.michel.lab.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/labplan/echantillon")
public class EchantillonController {
	
	@GetMapping("/creation")
	public String creation(Model model, HttpSession session) {
		
		return "ok";
	}

}
