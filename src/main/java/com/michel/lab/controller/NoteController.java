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

import com.michel.lab.constants.Constants;
import com.michel.lab.model.FormNote;

import com.michel.lab.model.NoteAux;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/note")
public class NoteController {
	
	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	
	@GetMapping("/liste/{id}")
	public String listerNotes(
			@PathVariable(name="id") Integer numQualification
			, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(numQualification);
		model.addAttribute("qualification", qualification);
		List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(numQualification);
		System.out.println("Taille liste de notes: " + notes.size()); 
		model.addAttribute("notes", notes);
		
		if(notes.isEmpty()) {
			
			model.addAttribute("vide", true);
			
		}else {
			
			model.addAttribute("vide", false);
		}
		
		return Constants.NOTES;
	}
	
	@GetMapping("/ajouter/{id}")
	public String ajouterNote(    // accès au formulaire de création de note
			@PathVariable(name="id") Integer numQualification
			, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(numQualification);
		System.out.println("num qualification: " + qualification.getNumero());
		model.addAttribute("qualification", qualification);
		model.addAttribute("formNote", new FormNote());
		
		return Constants.CREATION_NOTE;
	}
	
	@PostMapping("/creer/{id}")
	public String CreerNote(    // enregistrement de la nouvelle note
			@PathVariable(name="id") Integer numQualification
			, Model model, HttpSession session
			, FormNote formNote) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		Integer auteur = utilisateur.getId();
		formNote.setAuteur(auteur);
		formNote.setQualification(numQualification);
	
		microServiceLab.ajouterNote(formNote);
		
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(numQualification);
		model.addAttribute("qualification", qualification);
		List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(numQualification);
		System.out.println("Taille liste de notes: " + notes.size()); 
		model.addAttribute("notes", notes);
		
		if(notes.isEmpty()) {
			
			model.addAttribute("vide", true);
			
		}else {
			
			model.addAttribute("vide", false);
		}
		
		return Constants.NOTES;
		
	}
	
	@GetMapping("/voir/{id}")
	public String afficherNote(
			@PathVariable(name="id") Integer idNote
			, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		NoteAux note = microServiceLab.obtenirNote(idNote);
		System.out.println("numéro de note: " + note.getNumero());
		model.addAttribute("note", note);
		return "note";
	}

}
