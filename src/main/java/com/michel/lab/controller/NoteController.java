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
	public String listerNotes(@PathVariable(name = "id") Integer numQualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			model.addAttribute("qualification", qualification);
			List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(token, numQualification);

			for (NoteAux n : notes) {

				String text = n.getTexte();

				if (text.length() > 12) {
					String texte = text.substring(0, 13);
					n.setTexte(texte);
				}

			}

			System.out.println("Taille liste de notes: " + notes.size());
			model.addAttribute("notes", notes);

			if (notes.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}

			return Constants.NOTES;

		} else {

			return "redirect:/labplan/connexion";
		}

	}

	@GetMapping("/ajouter/{id}")
	public String ajouterNote( // accès au formulaire de création de note
			@PathVariable(name = "id") Integer numQualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			System.out.println("num qualification: " + qualification.getNumero());
			model.addAttribute("qualification", qualification);
			model.addAttribute("formNote", new FormNote());
			return Constants.CREATION_NOTE;

		} else {

			return "redirect:/labplan/connexion";
		}

	}

	@PostMapping("/creer/{id}")
	public String CreerNote( // enregistrement de la nouvelle note
			@PathVariable(name = "id") Integer numQualification, Model model, HttpSession session, FormNote formNote) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			Integer auteur = utilisateur.getId();
			formNote.setAuteur(auteur);
			formNote.setQualification(numQualification);

			microServiceLab.ajouterNote(token, formNote);

			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			model.addAttribute("qualification", qualification);
			List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(token, numQualification);

			for (NoteAux n : notes) {

				String text = n.getTexte();

				if (text.length() > 12) {
					String texte = text.substring(0, 13);
					n.setTexte(texte);
				}

			}

			System.out.println("Taille liste de notes: " + notes.size());
			model.addAttribute("notes", notes);

			if (notes.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}
			return Constants.NOTES;

		} else {

			return "redirect:/labplan/connexion";
		}

	}

	@GetMapping("/voir/{id}")
	public String afficherNote(@PathVariable(name = "id") Integer idNote, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			NoteAux note = microServiceLab.obtenirNote(token, idNote);
			System.out.println("numéro de note: " + note.getNumero());
			model.addAttribute("note", note);
			return "note2";

		} else {

			return "redirect:/labplan/connexion";
		}

		// return Constants.NOTE;
	}

	@GetMapping("/supprimer/{id}")
	public String supprimerNote(@PathVariable(name = "id") Integer idNote, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			NoteAux note = microServiceLab.obtenirNote(token, idNote);

			Integer numQualification = note.getQualification();

			microServiceLab.supprimerNote(token, idNote);

			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			model.addAttribute("qualification", qualification);
			List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(token, numQualification);

			for (NoteAux n : notes) {

				String text = n.getTexte();

				if (text.length() > 12) {
					String texte = text.substring(0, 13);
					n.setTexte(texte);
				}

			}
			System.out.println("Taille liste de notes: " + notes.size());
			model.addAttribute("notes", notes);

			if (notes.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}
			return Constants.NOTES;

		} else {

			return "redirect:/labplan/connexion";
		}

	}

	@GetMapping("/modifier/{id}")
	public String modifierNote(@PathVariable(name = "id") Integer idNote, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			NoteAux note = microServiceLab.obtenirNote(token, idNote);
			Integer numQualification = note.getQualification();
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			model.addAttribute("qualification", qualification);

			FormNote formNote = new FormNote();
			formNote.setId(idNote);
			formNote.setTexte(note.getTexte());
			formNote.setDate(note.getDate());
			formNote.setQualification(numQualification);

			model.addAttribute("formNote", formNote);
			return Constants.MODIFICATION_NOTE;

		} else {

			return "redirect:/labplan/connexion";
		}

	}

	@PostMapping("/modifier/{id}/{note}")
	public String modifierNote( // enregistrement de la nouvelle note
			@PathVariable(name = "id") Integer numQualification, @PathVariable(name = "note") Integer idNote,
			Model model, HttpSession session, FormNote formNote) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			Integer auteur = utilisateur.getId();
			formNote.setAuteur(auteur);
			formNote.setId(idNote);

			NoteAux note = microServiceLab.obtenirNote(token, idNote);

			System.out.println("id note récupéré: " + formNote.getId());

			microServiceLab.modifierNote(token, formNote);

			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);
			model.addAttribute("qualification", qualification);
			List<NoteAux> notes = microServiceLab.obtenirListeNotesParQualification(token, numQualification);
			System.out.println("Taille liste de notes: " + notes.size());
			model.addAttribute("notes", notes);

			if (notes.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}

			return Constants.NOTES;

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
