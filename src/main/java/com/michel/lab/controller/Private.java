package com.michel.lab.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.bouncycastle.crypto.engines.SM2Engine.Mode;
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
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.FormSequence;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.SequenceAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private")
public class Private {
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	@Autowired
	private UserConnexion userConnexion;
	
	
	@GetMapping("/qualification/creation")  // Accès au formulaire de création d'un qualification
	public String creer(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formQualif", new FormQualif());
		return Constants.CREATION_QUALIFICATION;
	}

	
	@PostMapping("/qualification/creation")  // Enregistrement des éléments de création d'une qualification
	public String enregistrerQualification(Model model, HttpSession session, FormQualif formQualif) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		Integer createurId = utilisateur.getId();
		formQualif.setCreateurId(createurId);
		microServiceLab.saveQualification(formQualif);
		return Constants.ESPACE_PERSONEL;
	}
	
	@GetMapping("/qualifications")
	public String qualifications(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.toutesLesQualifications();
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "1");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/historique/{id}")       // récupération de la liste de toutes les qualifications
										  // id = identifiant de l'utilisateur
	public String mesQualifications(@PathVariable (name = "id") Integer id, Model model, HttpSession session){
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualifications(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "2");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/qualifications/{id}")   // Récupérer les qualification d'un utilisateur, identifiant : id
											// id = identifiant de l'utilisateur
	public String qualificationsEnCours(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualificationsEnCours(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "3");
		return Constants.QUALIFICATIONS;
	}
	
	@GetMapping("/qualification/{id}")  // Consulter une qualification
										// id = identifiant de l'utilisateur
	public String qualification(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualification);
		
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
	}
	
	@GetMapping("/essai/voir/{id}")
	public String visualiserEssais(@PathVariable (name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		
		List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(id);
		
		model.addAttribute("qualification", qualification);
		model.addAttribute("essais", essais);
		return Constants.PAGE_ESSAIS;
	}
	
	@GetMapping("/sequences/{id}/{num}/{domaine}")  // id = numéro qualification, num = id essai
	public String voirSequencesParEssais(@PathVariable (name = "id") Integer id,
			@PathVariable (name = "num") Integer num,
		//	@PathVariable (name = "domaine") String domaine,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(num);
		model.addAttribute("essai", essai);
		//List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(id, num, domaine);
		List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(id, num);
		model.addAttribute("sequences", sequences);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(id);
		model.addAttribute("qualification", qualification);
		
		if (sequences.isEmpty()) {
			
			model.addAttribute("vide", true);
			
		}else {
			
			model.addAttribute("vide", false);
		}
		
		return Constants.LISTE_SEQUENCES;
	}
	
	@GetMapping("/sequence/creer/{id}/{qualification}/{domaine}")
	public String creerSequence(@PathVariable (name = "id") Integer id,  // id = identifiant essai
			@PathVariable (name = "qualification") Integer qualification,
			@PathVariable (name = "domaine") String domaine,
			Model model, HttpSession session) {
		
		System.out.println("Get: creerSequence");
		System.out.println("Valeur id récupéré: " + id);
		System.out.println("Valeur qualification récupéré: " + qualification);
		System.out.println("Valeur domaine récupéré: " + domaine);
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FormSequence formSequence = new FormSequence();
		formSequence.setEssai(id);
		formSequence.setQualification(qualification);
		formSequence.setNomDomaine(domaine);
		System.out.println(formSequence.toString());
		model.addAttribute("formSequence", formSequence);
		model.addAttribute("id", id);
		model.addAttribute("qualification", qualification);
		model.addAttribute("domaine", domaine);
		
		return "createSequence";
		
	}
	
	@PostMapping("/sequence/creer/{id}/{qualification}")
	public String enregistrerSequence(@PathVariable (name = "id") Integer id,
			@PathVariable (name = "qualification") Integer qualification,
			//@PathVariable (name = "domaine") String domaine,
		
			Model model, HttpSession session,
			FormSequence formSequence
			,RedirectAttributes redirectAttributes
			) {
		
		
		System.out.println("méthode POST enregistrement sequence");
		System.out.println(formSequence.toString());

		
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		System.out.println("Identifiant essai récupéré: " + id);
		System.out.println("Identifiant qualification récupéré: " + qualification);
		//System.out.println("Identifiant domaine récupéré: " + domaine);
		
		formSequence.setEssai(id);
		microServiceLab.enregistrerSequence(formSequence);
		
		
		redirectAttributes.addAttribute("id", qualification );
		redirectAttributes.addAttribute("num", id);
		//redirectAttributes.addAttribute("domaine", domaine );
		
		
		return "redirect:/labplan/private/sequences";
		
		//return "ok";
	}
	
	
	
	@GetMapping("/sequences")  //  id = id essai
	public String voirSequencesParEssais2(	
			@RequestParam (name = "id") Integer qualification,
			@RequestParam (name = "num") Integer id,
			//@RequestParam (name = "domaine") String nomDomaine,
			Model model, HttpSession session) {
		
		System.out.println(" *** entrée méthode voirSequencesParEssais2 ");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(id);
		model.addAttribute("essai", essai);
		//List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(qualification, id, null);
		List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(qualification, id);
		model.addAttribute("sequences", sequences);
		QualificationAux qualif = microServiceLab.obtenirQualificationParNumero(qualification);
		model.addAttribute("qualification", qualif);
		
		if (sequences.isEmpty()) {
			
			model.addAttribute("vide", true);
			
		}else {
			
			model.addAttribute("vide", false);
		}
		
		return Constants.LISTE_SEQUENCES;
	}
	
	
}
